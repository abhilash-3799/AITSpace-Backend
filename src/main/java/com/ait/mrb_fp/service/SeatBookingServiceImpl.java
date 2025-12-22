package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Notification;
import com.ait.mrb_fp.entity.Seat;
import com.ait.mrb_fp.entity.SeatBooking;
import com.ait.mrb_fp.exception.*;
import com.ait.mrb_fp.mapper.SeatBookingMapper;
import com.ait.mrb_fp.repository.EmployeeRepository;
import com.ait.mrb_fp.repository.SeatBookingRepository;
import com.ait.mrb_fp.repository.SeatRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatBookingServiceImpl implements SeatBookingService {

    private final SeatBookingRepository seatBookingRepo;
    private final SeatRepository seatRepo;
    private final EmployeeRepository employeeRepo;
    private final NotificationService notificationService;

    public SeatBookingServiceImpl(SeatBookingRepository seatBookingRepo,
                                  SeatRepository seatRepo,
                                  EmployeeRepository employeeRepo,
                                  NotificationService notificationService) {
        this.seatBookingRepo = seatBookingRepo;
        this.seatRepo = seatRepo;
        this.employeeRepo = employeeRepo;
        this.notificationService = notificationService;
    }

    @Override
    public SeatBookingResponseDTO create(SeatBookingRequestDTO dto) {
        try {
            if (dto.getSeatId() == null || dto.getEmployeeId() == null) {
                throw new BadRequestException("Seat ID and Employee ID must not be null");
            }

            Seat seat = seatRepo.findById(dto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));

            Employee employee = employeeRepo.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

            LocalDateTime requestedDateTime = dto.getSeatBookingDate() != null ? dto.getSeatBookingDate() : LocalDateTime.now();
            LocalDate requestedDate = requestedDateTime.toLocalDate();
            LocalDateTime startOfDay = requestedDate.atStartOfDay();
            LocalDateTime endOfDay = requestedDate.atTime(LocalTime.MAX);

            boolean isSeatBookedByAnotherEmployeeSameDay =
                    seatBookingRepo.existsBySeatAndDateRange(seat.getSeatId(), startOfDay, endOfDay)
                            && seatBookingRepo.existsBySeat_SeatIdAndSeatBookingDateAndEmployee_EmployeeIdNot(
                            seat.getSeatId(),
                            requestedDateTime,
                            employee.getEmployeeId()
                    );

            if (isSeatBookedByAnotherEmployeeSameDay) {
                throw new BookingConflictException("Seat is already booked by another employee for the selected date.");
            }

            boolean isSameEmployeeBookedSameSeatSameDate =
                    seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatIdAndSeatBookingDate(
                            employee.getEmployeeId(),
                            seat.getSeatId(),
                            requestedDateTime
                    );

            if (isSameEmployeeBookedSameSeatSameDate) {
                throw new BookingConflictException("You have already booked this seat for the same date.");
            }

            boolean isBookedNow = seatBookingRepo.existsBySeat_SeatIdAndSeatBookingDate(seat.getSeatId(), LocalDateTime.now());
            if (isBookedNow) {
                throw new BookingConflictException("Seat is already booked right now.");
            }

            boolean hasExistingBooking = seatBookingRepo.existsByEmployee_EmployeeId(employee.getEmployeeId());

            if (hasExistingBooking) {
                if (employee.isTeamLead()) {

                    boolean alreadyBookedThisSeat = seatBookingRepo
                            .existsByEmployee_EmployeeIdAndSeat_SeatId(employee.getEmployeeId(), seat.getSeatId());

                    if (alreadyBookedThisSeat) {
                        boolean sameDayBooking =
                                seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatIdAndSeatBookingDate(
                                        employee.getEmployeeId(),
                                        seat.getSeatId(),
                                        requestedDateTime
                                );

                        if (sameDayBooking) {
                            throw new BookingConflictException("Team Lead already booked this seat for the same date.");
                        }
                    }

                    boolean seatTakenThatDay =
                            seatBookingRepo.existsBySeatAndDateRange(seat.getSeatId(), startOfDay, endOfDay);

                    if (seatTakenThatDay) {
                        throw new InvalidStateException("Seat is already allocated to another employee for this date.");
                    }

                } else {
                    throw new BookingConflictException("Employee can only book one seat at a time.");
                }
            }

            boolean seatTakenThatDay =
                    seatBookingRepo.existsBySeatAndDateRange(seat.getSeatId(), startOfDay, endOfDay);
            if (seatTakenThatDay) {
                throw new InvalidStateException("Seat is already allocated to another employee for this date.");
            }

            SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
            booking.setSeatBookingDate(requestedDateTime);
            seatBookingRepo.save(booking);

            seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
            seat.setAvailable(false);
            seatRepo.save(seat);

            sendSeatBookedNotification(employee,seat);

            return SeatBookingMapper.toResponse(booking);

        } catch (InvalidStateException | BookingConflictException | ResourceNotFoundException |
                 BadRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid request data: " + ex.getMessage());
        } catch (Exception ex) {
            throw new InternalServerException("Unexpected error occurred: " + ex.getMessage());
        }
    }

    @Override
    public SeatBookingResponseDTO getById(String id) {
        SeatBooking booking = seatBookingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
        return SeatBookingMapper.toResponse(booking);
    }

    @Override
    public List<SeatBookingResponseDTO> getAll() {
        try {
            return seatBookingRepo.findAll()
                    .stream()
                    .map(SeatBookingMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseException("Failed to fetch seat bookings from database.");
        }
    }

    @Override
    public SeatBookingResponseDTO update(String id, SeatBookingRequestDTO dto) {
        try {
            SeatBooking existing = seatBookingRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));

            Seat seat = seatRepo.findById(dto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));

            Employee employee = employeeRepo.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

            SeatBookingMapper.updateEntity(existing, dto, seat, employee);
            seatBookingRepo.save(existing);

            return SeatBookingMapper.toResponse(existing);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed: " + ex.getMessage());
        } catch (Exception ex) {
            throw new InternalServerException("Unexpected error occurred while updating: " + ex.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (!seatBookingRepo.existsById(id)) {
                throw new ResourceNotFoundException("Booking not found with ID: " + id);
            }
            seatBookingRepo.deleteById(id);
        } catch (DataAccessException ex) {
            throw new ForeignKeyConstraintException("Failed to delete booking due to foreign key constraint.");
        } catch (Exception ex) {
            throw new InternalServerException("Unexpected error while deleting booking: " + ex.getMessage());
        }
    }

        @Override
        @Transactional
    public List<SeatBookingResponseDTO> createBulk(List<SeatBookingRequestDTO> dtos, String teamLeadId)
    {
        Employee teamLead = employeeRepo.findByIdWithRelations(teamLeadId);

        if (teamLead == null) {
            throw new ResourceNotFoundException("Team Lead not found with ID: " + teamLeadId);
        }

        if (!teamLead.isTeamLead())
        {
            throw new UnauthorizedException("Only Team Leads can perform bulk bookings.");
        }



        return dtos.stream().map(dto ->
        {
            LocalDateTime bookingDate = dto.getSeatBookingDate() != null ? dto.getSeatBookingDate() : LocalDateTime.now();
            LocalDate date = bookingDate.toLocalDate();
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);


            Seat seat = seatRepo.findById(dto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));

            Employee employee = employeeRepo.findByIdWithRelations(dto.getEmployeeId());
            if (employee == null) {
                throw new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId());
            }
            boolean seatAlreadyBookedSameDay =
                    seatBookingRepo.existsBySeatAndDateRange(seat.getSeatId(), startOfDay, endOfDay);

            SeatBooking existingBooking =
                    seatBookingRepo.findFirstBySeat_SeatIdAndSeatBookingDateBetween(
                            seat.getSeatId(), startOfDay, endOfDay);

            if (existingBooking != null) {

                Employee bookedBy = existingBooking.getEmployee();


                boolean isBookedByTeamLead = bookedBy.isTeamLead();


                boolean isDifferentTeam = bookedBy.getTeam() != null &&
                        teamLead.getTeam() != null &&
                        !bookedBy.getTeam().getTeamId().equals(teamLead.getTeam().getTeamId());

                if (isBookedByTeamLead && isDifferentTeam) {
                    throw new BookingConflictException(
                            "Another Team Lead has already booked this seat for employee " +
                                    bookedBy.getFirstName() +
                                    " (" + bookedBy.getEmployeeId() + ")."
                    );
                }


                throw new BookingConflictException(
                        "Seat " + seat.getSeatId() + " is already booked for this date."
                );
            }


            boolean employeeAlreadyBookedSameDate =
                    seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatIdAndSeatBookingDate(
                            employee.getEmployeeId(),
                            seat.getSeatId(),
                            bookingDate
                    );

            if (employeeAlreadyBookedSameDate) {
                throw new BookingConflictException(
                        "Employee " + employee.getFirstName() + " already booked a seat for this date."
                );
            }

            boolean employeeHasAnotherSeatSameDay =
                    seatBookingRepo.existsByEmployee_EmployeeIdAndSeatBookingDateBetween(
                            employee.getEmployeeId(),
                            startOfDay,
                            endOfDay
                    );

            if (employeeHasAnotherSeatSameDay) {
                throw new BookingConflictException(
                        "Employee " + employee.getFirstName() + " "+employee.getEmployeeId()+" "+seat.getSeatId()+" is already booked for another seat on this date."
                );
            }
            boolean anotherLeadBooked =
                    seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatId(employee.getEmployeeId(), seat.getSeatId())
                            && !employee.getTeam().getTeamId().equals(teamLead.getTeam().getTeamId());

            if (anotherLeadBooked) {
                throw new BookingConflictException(
                        "Another Team Lead has already booked this seat for employee " +
                                employee.getFirstName() + "."
                );
            }


            boolean isBooked = seatBookingRepo.existsBySeat_SeatIdAndSeatBookingDate(seat.getSeatId(), LocalDateTime.now());
            if (isBooked)
            {
                sendSeatNotAvailableNotification(employee,seat,"Seat alredy booked for this date");
                throw new BookingConflictException("Seat already booked: " + seat.getSeatId());
            }



            SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
            seatBookingRepo.save(booking);

            seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
            seat.setAvailable(false);
            seatRepo.save(seat);

            sendSeatBookedNotification(employee, seat);
            return SeatBookingMapper.toResponse(booking);
        }).collect(Collectors.toList());
    }

      private void sendSeatBookedNotification(Employee employee, Seat seat)
       {
             NotificationRequestDTO dto = NotificationRequestDTO.builder()
            .employeeId(employee.getEmployeeId())
            .title("Seat Booked Successfully")
            .notificationType(Notification.NotificationType.SEAT_ASSIGNMENT)
            .seatNumber(seat.getSeatId())
            .message("Your seat " + seat.getSeatId() + " has been booked Successfully.")
            .build();

            notificationService.create(dto);
}


    private void sendSeatNotAvailableNotification(Employee employee, Seat seat, String reason)
    {
        NotificationRequestDTO dto = NotificationRequestDTO.builder()
                .employeeId(employee.getEmployeeId())
                .title("Seat Not Available")
                .message("Seat " + seat.getSeatNumber() + " is not available. " + reason)
                .build();

        notificationService.create(dto);
    }
}
