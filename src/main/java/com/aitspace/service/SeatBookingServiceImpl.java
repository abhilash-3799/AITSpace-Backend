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
            throw new BookingConflictException("Seat already booked");
        }

        SeatBooking booking = SeatBooking.builder()
                .seat(seat)
                .employee(employee)
                .startDateTime(start)
                .endDateTime(end)
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
}
