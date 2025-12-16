package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Notification;
import com.ait.mrb_fp.mapper.NotificationMapper;
import com.ait.mrb_fp.repository.EmployeeRepository;
import com.ait.mrb_fp.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   EmployeeRepository employeeRepository) {
        this.notificationRepository = notificationRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<NotificationResponseDTO> getAll() {
        log.info("Fetching all notifications");
        try {
            List<NotificationResponseDTO> notifications = notificationRepository.findAll()
                    .stream()
                    .map(NotificationMapper::toResponse)
                    .collect(Collectors.toList());
            log.info("Total notifications fetched: {}", notifications.size());
            return notifications;
        } catch (Exception ex) {
            log.error("Error fetching notifications: {}", ex.getMessage());
            throw new RuntimeException("Error fetching notifications.");
        }
    }

    @Override
    public NotificationResponseDTO getById(String id) {
        log.info("Fetching notification by ID: {}", id);
        try {
            Notification notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found: " + id));
            log.info("Notification found: {}", id);
            return NotificationMapper.toResponse(notification);
        } catch (Exception ex) {
            log.error("Error fetching notification with ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error fetching notification with ID: " + id);
        }
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
            return NotificationMapper.toResponse(notification);
        } catch (Exception ex) {
            log.error("Error creating notification: {}", ex.getMessage());
            throw new RuntimeException("Error creating notification.");
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
}
