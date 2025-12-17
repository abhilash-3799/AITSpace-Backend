package com.aitspace.service;

import com.aitspace.dto.request.NotificationRequestDTO;
import com.aitspace.dto.request.SeatCancellationRequestDTO;
import com.aitspace.dto.response.NotificationResponseDTO;
import com.aitspace.entity.Employee;
import com.aitspace.entity.Notification;
import com.aitspace.mapper.NotificationMapper;
import com.aitspace.repository.EmployeeRepository;
import com.aitspace.repository.NotificationRepository;
import com.aitspace.repository.SeatBookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;
    private final SeatBookingRepository seatBookingRepository;
    private final EmailService emailService;


    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   EmployeeRepository employeeRepository, SeatBookingRepository seatBookingRepository,EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.employeeRepository = employeeRepository;
        this.seatBookingRepository = seatBookingRepository;
        this.emailService = emailService;
    }


@Override
public List<NotificationResponseDTO> getAll() {
    log.info("Fetching all notifications");
    return notificationRepository.findAllWithEmployee()
            .stream()
            .map(NotificationMapper::toResponse)
            .collect(Collectors.toList());
}



    @Override
    public NotificationResponseDTO getById(String id) {
        log.info("Fetching notification by ID: {}", id);

        Notification notification = notificationRepository.findByIdWithEmployee(id);

        if (notification == null) {
            throw new RuntimeException("Notification not found: " + id);
        }

        return NotificationMapper.toResponse(notification);
    }

    @Override
    public NotificationResponseDTO create(NotificationRequestDTO request) {
        log.info("Creating notification for employee ID: {}", request.getEmployeeId());
        try {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getEmployeeId()));

            Notification notification = NotificationMapper.toEntity(request, employee);
            notificationRepository.save(notification);

            log.info("Notification created successfully for employee ID: {}", request.getEmployeeId());
            sendNotificationEmail(employee, notification);
            return NotificationMapper.toResponse(notification);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error creating notification: {}", ex.getMessage());
            throw new RuntimeException("Error creating notification: " + ex.getMessage());
        }
    }

    @Override
    public NotificationResponseDTO update(String id, NotificationRequestDTO request) {
        log.info("Updating notification with ID: {}", id);
        try {
            Notification existing = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found: " + id));

            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getEmployeeId()));

            NotificationMapper.updateEntity(existing, request, employee);
            notificationRepository.save(existing);

            log.info("Notification updated successfully: {}", id);
            return NotificationMapper.toResponse(existing);
        } catch (Exception ex) {
            log.error("Error updating notification with ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error updating notification with ID: " + id);
        }
    }

    @Override
    public void delete(String id) {
        log.warn("Deleting notification with ID: {}", id);
        try {
            notificationRepository.deleteById(id);
            log.info("Notification deleted successfully: {}", id);
        } catch (Exception ex) {
            log.error("Error deleting notification with ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error deleting notification with ID: " + id);
        }
    }


    @Override
    public List<NotificationResponseDTO> getByType(String type) {
        log.info("Fetching notifications by type: {}", type);
        try {

            Notification.NotificationType notificationType =
                    Notification.NotificationType.valueOf(type.toUpperCase());


            List<Notification> notifications = notificationRepository.findByNotificationType(notificationType);

            log.info("Found {} notifications of type: {}", notifications.size(), type);


            return notifications.stream()
                    .map(NotificationMapper::toResponse)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException ex) {
            log.error("Invalid notification type: {}", type);
            throw new RuntimeException("Invalid notification type: " + type +
                    ". Valid types are: " + Arrays.toString(Notification.NotificationType.values()));
        } catch (Exception ex) {
            log.error("Error fetching notifications by type {}: {}", type, ex.getMessage());
            throw new RuntimeException("Error fetching notifications by type: " + type);
        }
    }
    @Override
    public NotificationResponseDTO createSeatCancellation(SeatCancellationRequestDTO request) {
        log.info("Creating seat cancellation for employee ID: {}, seat: {}",
                request.getEmployeeId(), request.getSeatNumber());

        try {

            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getEmployeeId()));






            List<Notification> existingCancellations = notificationRepository
                    .findBySeatNumberAndActiveTrue(request.getSeatNumber());


            existingCancellations.forEach(n -> {
                n.setActive(false);
                notificationRepository.save(n);
            });

            NotificationRequestDTO notificationRequest = NotificationRequestDTO.builder()
                    .employeeId(request.getEmployeeId())
                    .title("Seat Cancelled - " + request.getSeatNumber())
                    .message(buildSeatCancellationMessage(request))
                    .seatNumber(request.getSeatNumber())
                    .cancellationReason(request.getCancellationReason())
                    .notificationType(Notification.NotificationType.SEAT_CANCELLATION)
                    .build();


            Notification notification = NotificationMapper.toEntity(notificationRequest, employee);
            notificationRepository.save(notification);
            sendNotificationEmail(employee, notification);



            return NotificationMapper.toResponse(notification);

        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creating seat cancellation: {}", ex.getMessage());
            throw new RuntimeException("Error creating seat cancellation notification.");
        }
    }



    @Override
    public List<NotificationResponseDTO> getSeatCancellationNotifications() {
        log.info("Fetching all seat cancellation notifications");
        return notificationRepository.findSeatCancellationNotifications()
                .stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getSeatCancellationsByEmployee(String employeeId) {
        log.info("Fetching seat cancellations for employee ID: {}", employeeId);


        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found: " + employeeId);
        }

        return notificationRepository.findByEmployeeIdAndType(employeeId,
                        Notification.NotificationType.SEAT_CANCELLATION)
                .stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDTO cancelSeatCancellation(String notificationId) {
        log.info("Cancelling seat cancellation notification with ID: {}", notificationId);

        try {
            Notification notification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new RuntimeException("Notification not found: " + notificationId));


            if (notification.getNotificationType() != Notification.NotificationType.SEAT_CANCELLATION) {
                throw new RuntimeException("Notification is not a seat cancellation: " + notificationId);
            }


            notification.setActive(false);
            notificationRepository.save(notification);

            log.info("Seat cancellation deactivated successfully: {}", notificationId);
            return NotificationMapper.toResponse(notification);

        } catch (Exception ex) {
            log.error("Error cancelling seat cancellation with ID {}: {}", notificationId, ex.getMessage());
            throw new RuntimeException("Error cancelling seat cancellation: " + ex.getMessage());
        }
    }

    private String buildSeatCancellationMessage(SeatCancellationRequestDTO request) {
        StringBuilder message = new StringBuilder();
        message.append("Seat ").append(request.getSeatNumber()).append(" has been cancelled. ");
        message.append("Reason: ").append(request.getCancellationReason());

        if (request.getAdditionalNotes() != null && !request.getAdditionalNotes().trim().isEmpty()) {
            message.append(". Additional notes: ").append(request.getAdditionalNotes());
        }

        return message.toString();
    }
    private void sendNotificationEmail(Employee employee, Notification notification) {

        String subject = "AITSpace Notification - " + notification.getNotificationType();

        String fullName = employee.getFirstName() + " " + employee.getLastName();

        String body = "Hello " + fullName + ",\n\n"

                + notification.getMessage() + "\n\n"
//                +notification.getNotificationType()+"\n\n"
                + "Seat Id: " + (notification.getSeatNumber() != null ? notification.getSeatNumber() : "N/A") + "\n\n"
                + "Regards,\nAITSpace Team";

        emailService.sendEmail(employee.getEmail(), subject, body);
//        emailService.sendEmail("abhilash.dhamdhere37@gmail.com", subject, body);

        log.info("Email sent to {} for notification {}", employee.getEmail(), notification.getNotificationId());
    }


}
