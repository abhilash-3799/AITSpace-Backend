package com.aitspace.service;

import com.aitspace.dto.request.SeatBookingRequestDTO;
import com.aitspace.dto.response.SeatBookingResponseDTO;
import com.aitspace.entity.Employee;
import com.aitspace.entity.Seat;
import com.aitspace.entity.SeatBooking;
import com.aitspace.exception.BadRequestException;
import com.aitspace.exception.BookingConflictException;
import com.aitspace.exception.ResourceNotFoundException;
import com.aitspace.mapper.SeatBookingMapper;
import com.aitspace.repository.EmployeeRepository;
import com.aitspace.repository.SeatBookingRepository;
import com.aitspace.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatBookingServiceImpl implements SeatBookingService {

    private final SeatBookingRepository bookingRepo;
    private final SeatRepository seatRepo;
    private final EmployeeRepository employeeRepo;

    @Override
    public SeatBookingResponseDTO create(SeatBookingRequestDTO dto) {
        Seat seat = seatRepo
                .findBySeatNumberAndOffice_OfficeName(
                        dto.getSeatNumber(),
                        dto.getOfficeName()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found"));

        Employee employee = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        LocalDateTime start =
                LocalDateTime.of(dto.getBookingDate(), dto.getStartTime());
        LocalDateTime end =
                LocalDateTime.of(dto.getBookingDate(), dto.getEndTime());

        if (!end.isAfter(start)) {
            throw new BadRequestException("End time must be after start time");
        }

        if (start.isBefore(LocalDateTime.now())) {
            throw new BookingConflictException("Booking time is in the past");
        }

        if (bookingRepo.existsOverlappingBooking(seat.getSeatId(), start, end)) {
            throw new BookingConflictException("Seat already booked for the selected time");
        }

        SeatBooking booking = SeatBooking.builder()
                .seat(seat)
                .employee(employee)
                .seatBookingDate(dto.getBookingDate())
                .startTime(start)
                .endTime(end)
                .build();

        bookingRepo.save(booking);

        seat.setAvailable(false);
        seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
        seatRepo.save(seat);

        return SeatBookingMapper.toResponse(booking);
    }

    @Override
    public void cancel(String bookingId) {
        SeatBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        if (!booking.isActive()) {
            return;
        }

        booking.setActive(false);
        booking.setStatus(SeatBooking.BookingStatus.CANCELLED);
        booking.setDeletedAt(LocalDateTime.now());

        Seat seat = booking.getSeat();
        seat.setAvailable(true);
        seat.setSeatStatus(Seat.SeatStatus.UNALLOCATED);

        bookingRepo.save(booking);
        seatRepo.save(seat);
    }

    @Override
    public List<SeatBookingResponseDTO> getAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(SeatBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatBookingResponseDTO getBookingById(String bookingId) {
        SeatBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found with id: " + bookingId));
        return SeatBookingMapper.toResponse(booking);
    }

    @Override
    public SeatBookingResponseDTO updateBooking(String bookingId, SeatBookingRequestDTO dto) {
        SeatBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        if (!booking.isActive()) {
            throw new BadRequestException("Cannot update a cancelled booking");
        }

        Seat seat = seatRepo
                .findBySeatNumberAndOffice_OfficeName(
                        dto.getSeatNumber(),
                        dto.getOfficeName()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found"));

        Employee employee = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        LocalDateTime start =
                LocalDateTime.of(dto.getBookingDate(), dto.getStartTime());
        LocalDateTime end =
                LocalDateTime.of(dto.getBookingDate(), dto.getEndTime());

        if (!end.isAfter(start)) {
            throw new BadRequestException("End time must be after start time");
        }

        if (start.isBefore(LocalDateTime.now())) {
            throw new BookingConflictException("Booking time is in the past");
        }

        // Check for overlapping bookings excluding current booking
        if (bookingRepo.existsOverlappingBookingExcludingCurrent(seat.getSeatId(), start, end, bookingId)) {
            throw new BookingConflictException("Seat already booked for the selected time");
        }

        // Update old seat status
        Seat oldSeat = booking.getSeat();
        if (!oldSeat.getSeatId().equals(seat.getSeatId())) {
            oldSeat.setAvailable(true);
            oldSeat.setSeatStatus(Seat.SeatStatus.UNALLOCATED);
            seatRepo.save(oldSeat);
        }

        // Update booking
        booking.setSeat(seat);
        booking.setEmployee(employee);
        booking.setSeatBookingDate(dto.getBookingDate());
        booking.setStartTime(start);
        booking.setEndTime(end);

        bookingRepo.save(booking);

        // Update new seat status
        seat.setAvailable(false);
        seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
        seatRepo.save(seat);

        return SeatBookingMapper.toResponse(booking);
    }

    @Override
    public List<SeatBookingResponseDTO> createBulkBooking(List<SeatBookingRequestDTO> dtos) {
        return dtos.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}