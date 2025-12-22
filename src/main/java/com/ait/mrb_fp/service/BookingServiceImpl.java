package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.BookingRequestDTO;
import com.ait.mrb_fp.dto.response.AvailabilityResponseDTO;
import com.ait.mrb_fp.dto.response.BookingResponseDTO;
import com.ait.mrb_fp.entity.Booking;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.exception.BookingConflictException;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.repository.BookingRepository;
import com.ait.mrb_fp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;

    /* =====================================================
       CREATE BOOKING
       ===================================================== */
    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO request) {

        // Find employee by ID from request
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + request.getEmployeeId()
                        )
                );

        LocalTime startTime = request.getStartTimeAsLocalTime();
        LocalTime endTime = request.getEndTimeAsLocalTime();

        validateBookingTime(request.getDate(), startTime, endTime);

        // Overlapping booking check
        if (!bookingRepository.findOverlappingBookings(
                request.getRoomName(),
                request.getDate(),
                startTime,
                endTime
        ).isEmpty()) {
            throw new BookingConflictException(
                    "Room is already booked for the selected time slot"
            );
        }

        // REMOVED email uniqueness check since email comes from logged-in user

        Booking booking = Booking.builder()
                .employee(employee)
                .type(request.getType())
                .roomName(request.getRoomName())
                .floor(request.getFloor())
                .capacity(request.getCapacity())
                .date(request.getDate())
                .startTime(startTime)
                .endTime(endTime)
                .amenities(
                        request.getAmenities() != null
                                ? String.join(",", request.getAmenities())
                                : null
                )
                .officeName(request.getOfficeName())
                .attendees(request.getAttendees())
                .email(employee.getEmail()) // Use email from employee entity (logged-in user)
                .status("Active")
                .reminderSent(false)
                .build();

        Booking saved = bookingRepository.save(booking);

        log.info(
                "Booking created | bookingId={} | employeeId={} | email={}",
                saved.getBookingIdString(),
                employee.getEmployeeId(),
                employee.getEmail()
        );

        return toResponse(saved);
    }

    /* =====================================================
       GET ALL BOOKINGS
       ===================================================== */
    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /* =====================================================
       GET BOOKING BY bookingIdString
       ===================================================== */
    @Override
    public BookingResponseDTO getBookingById(String bookingIdString) {
        Booking booking = bookingRepository.findAll()
                .stream()
                .filter(b -> bookingIdString.equals(b.getBookingIdString()))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with bookingId: " + bookingIdString
                        )
                );

        return toResponse(booking);
    }

    /* =====================================================
       BOOKINGS BY ROOM & DATE
       ===================================================== */
    @Override
    public List<BookingResponseDTO> getBookingsByRoomAndDate(
            String roomName,
            LocalDate date
    ) {
        return bookingRepository.findByRoomNameAndDate(roomName, date)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /* =====================================================
       CHECK AVAILABILITY
       ===================================================== */
    @Override
    public Boolean checkAvailability(BookingRequestDTO request) {

        LocalTime startTime = request.getStartTimeAsLocalTime();
        LocalTime endTime = request.getEndTimeAsLocalTime();

        return bookingRepository.findOverlappingBookings(
                request.getRoomName(),
                request.getDate(),
                startTime,
                endTime
        ).isEmpty();
    }

    /* =====================================================
       ROOM AVAILABILITY PERCENTAGE
       ===================================================== */
    @Override
    public AvailabilityResponseDTO getRoomAvailability(
            String roomName,
            LocalDate date
    ) {
        List<Booking> bookings =
                bookingRepository.findActiveBookingsForRoomOnDate(roomName, date);

        if (bookings.isEmpty()) {
            return new AvailabilityResponseDTO(
                    roomName,
                    0,
                    100,
                    "Available"
            );
        }

        int totalMinutesInDay = 24 * 60;
        int bookedMinutes = 0;

        for (Booking b : bookings) {
            bookedMinutes +=
                    (b.getEndTime().getHour() * 60 + b.getEndTime().getMinute())
                            -
                            (b.getStartTime().getHour() * 60 + b.getStartTime().getMinute());
        }

        int bookedPercent =
                Math.min((bookedMinutes * 100) / totalMinutesInDay, 100);
        int availablePercent = 100 - bookedPercent;

        String status =
                bookedPercent < 30 ? "Available" :
                        bookedPercent < 70 ? "Limited" :
                                "Fully Booked";

        return new AvailabilityResponseDTO(
                roomName,
                bookedPercent,
                availablePercent,
                status
        );
    }

    /* =====================================================
       CANCEL BOOKING (SOFT DELETE)
       ===================================================== */
    @Override
    public BookingResponseDTO cancelBooking(String bookingIdString) {

        Booking booking = bookingRepository.findAll()
                .stream()
                .filter(b -> bookingIdString.equals(b.getBookingIdString()))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with bookingId: " + bookingIdString
                        )
                );

        booking.setStatus("Cancelled");
        booking.setDeletedAt(java.time.LocalDateTime.now());

        Booking updated = bookingRepository.save(booking);

        log.info("Booking cancelled | bookingId={}", bookingIdString);

        return toResponse(updated);
    }

    /* =====================================================
       BOOKINGS BY OFFICE
       ===================================================== */
    @Override
    public List<BookingResponseDTO> getBookingsByOffice(String officeName) {
        return bookingRepository.findByOfficeNameAndStatus(officeName, "Active")
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /* =====================================================
       PRIVATE HELPERS
       ===================================================== */
    private void validateBookingTime(
            LocalDate date,
            LocalTime start,
            LocalTime end
    ) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Cannot book a room for a past date"
            );
        }

        if (!start.isBefore(end)) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }
    }

    private BookingResponseDTO toResponse(Booking booking) {
        return BookingResponseDTO.builder()
                .employeeId(booking.getEmployee().getEmployeeId())
                .type(booking.getType())
                .roomName(booking.getRoomName())
                .floor(booking.getFloor())
                .capacity(booking.getCapacity())
                .date(booking.getDate())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .amenities(booking.getAmenities())
                .status(booking.getStatus())
                .officeName(booking.getOfficeName())
                .reminderSent(booking.getReminderSent())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .deletedAt(booking.getDeletedAt())
                .attendees(booking.getAttendees())
                .email(booking.getEmail())
                .bookingIdString(booking.getBookingIdString())
                .build();
    }
}