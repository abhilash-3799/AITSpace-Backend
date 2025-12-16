package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.BookingRequestDTO;
import com.ait.mrb_fp.dto.response.BookingResponseDTO;
import com.ait.mrb_fp.dto.response.AvailabilityResponseDTO;
import com.ait.mrb_fp.entity.Booking;
import com.ait.mrb_fp.exception.BookingConflictException;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.repository.BookingRepository;
import com.ait.mrb_fp.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequest) {
        // Convert String times to LocalTime
        LocalTime startTime = bookingRequest.getStartTimeAsLocalTime();
        LocalTime endTime = bookingRequest.getEndTimeAsLocalTime();

        // Validate booking time with converted times
        validateBookingTime(bookingRequest, startTime, endTime);

        // Check for overlapping bookings using LocalTime
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                bookingRequest.getRoomName(),
                bookingRequest.getDate(),
                startTime,
                endTime
        );

        if (!overlappingBookings.isEmpty()) {
            throw new BookingConflictException("Room is already booked for the selected time slot");
        }

        // Convert DTO to Entity
        Booking booking = new Booking();
        booking.setType(bookingRequest.getType());
        booking.setRoomName(bookingRequest.getRoomName());
        booking.setFloor(bookingRequest.getFloor());
        booking.setCapacity(bookingRequest.getCapacity());
        booking.setDate(bookingRequest.getDate());
        booking.setStartTime(startTime);  // Use LocalTime
        booking.setEndTime(endTime);      // Use LocalTime
        booking.setAmenities(bookingRequest.getAmenities());
        booking.setOfficeName(bookingRequest.getOfficeName());
        booking.setReminderSent(false);
        booking.setAttendees(bookingRequest.getAttendees());
        booking.setEmail(bookingRequest.getEmail());

        // Save booking
        Booking savedBooking = bookingRepository.save(booking);

        log.info("Booking created successfully: {} for room: {} on {}",
                savedBooking.getBookingIdString(), savedBooking.getRoomName(), savedBooking.getDate());

        return convertToResponseDTO(savedBooking);
    }

    // Update validateBookingTime method to accept LocalTime parameters
    private void validateBookingTime(BookingRequestDTO bookingRequest, LocalTime startTime, LocalTime endTime) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        // Check if booking date is in the past
        if (bookingRequest.getDate().isBefore(today)) {
            throw new IllegalArgumentException("Cannot book a room for a past date");
        }

        // Check if booking time is in the past for today
        if (bookingRequest.getDate().isEqual(today) && startTime.isBefore(now)) {
            throw new IllegalArgumentException("Cannot book a room for past time");
        }

        // Check if end time is after start time
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }


    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDTO getBookingById(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return convertToResponseDTO(booking);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByRoomAndDate(String roomName, LocalDate date) {
        return bookingRepository.findByRoomNameAndDate(roomName, date)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AvailabilityResponseDTO getRoomAvailability(String roomName, LocalDate date) {
        List<Booking> bookings = bookingRepository.findActiveBookingsForRoomOnDate(roomName, date);

        if (bookings.isEmpty()) {
            return new AvailabilityResponseDTO(roomName, 0, 100, "Available");
        }

        // Calculate total booked minutes for the day
        int totalMinutesInDay = 24 * 60;
        int totalBookedMinutes = 0;

        for (Booking booking : bookings) {
            int startMinutes = booking.getStartTime().getHour() * 60 + booking.getStartTime().getMinute();
            int endMinutes = booking.getEndTime().getHour() * 60 + booking.getEndTime().getMinute();
            totalBookedMinutes += (endMinutes - startMinutes);
        }

        // Calculate percentages
        int bookedPercentage = Math.min((totalBookedMinutes * 100) / totalMinutesInDay, 100);
        int availablePercentage = 100 - bookedPercentage;

        String status;
        if (bookedPercentage < 30) {
            status = "Available";
        } else if (bookedPercentage < 70) {
            status = "Limited";
        } else {
            status = "Fully Booked";
        }

        return new AvailabilityResponseDTO(roomName, bookedPercentage, availablePercentage, status);
    }

    @Override
    @Transactional
    public BookingResponseDTO cancelBooking(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        booking.setStatus("Cancelled");
        Booking updatedBooking = bookingRepository.save(booking);

        log.info("Booking cancelled: {}", id);
        return convertToResponseDTO(updatedBooking);
    }

    @Override
    public Boolean checkAvailability(BookingRequestDTO bookingRequest) {
        LocalTime startTime = bookingRequest.getStartTimeAsLocalTime();
        LocalTime endTime = bookingRequest.getEndTimeAsLocalTime();

        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                bookingRequest.getRoomName(),
                bookingRequest.getDate(),
                startTime,
                endTime
        );
        return overlappingBookings.isEmpty();
    }

    @Override
    public List<BookingResponseDTO> getBookingsByOffice(String officeName) {
        return bookingRepository.findByOfficeNameAndStatus(officeName, "Active")
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private BookingResponseDTO convertToResponseDTO(Booking booking) {
        return new BookingResponseDTO(
                booking.getId(),
                booking.getType(),
                booking.getRoomName(),
                booking.getFloor(),
                booking.getCapacity(),
                booking.getDate(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getAmenities(),
                booking.getStatus(),
                booking.getOfficeName(),
                booking.getReminderSent(),
                booking.getBookedAt(),
                booking.getAttendees(),
                booking.getEmail(),
                booking.getBookingIdString()
        );
    }
}