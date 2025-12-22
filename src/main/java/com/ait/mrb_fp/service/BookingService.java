package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.BookingRequestDTO;
import com.ait.mrb_fp.dto.response.AvailabilityResponseDTO;
import com.ait.mrb_fp.dto.response.BookingResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    /**
     * Create a new booking linked to an employee
     */
    BookingResponseDTO createBooking(BookingRequestDTO bookingRequest);

    /**
     * Fetch all bookings
     */
    List<BookingResponseDTO> getAllBookings();

    /**
     * Fetch booking by booking reference ID (bookingIdString)
     */
    BookingResponseDTO getBookingById(String bookingIdString);

    /**
     * Fetch bookings for a room on a specific date
     */
    List<BookingResponseDTO> getBookingsByRoomAndDate(
            String roomName,
            LocalDate date
    );

    /**
     * Check room availability for a given request
     */
    Boolean checkAvailability(BookingRequestDTO bookingRequest);

    /**
     * Get room availability percentage for a given date
     */
    AvailabilityResponseDTO getRoomAvailability(
            String roomName,
            LocalDate date
    );

    /**
     * Cancel (soft delete) a booking
     */
    BookingResponseDTO cancelBooking(String bookingIdString);

    /**
     * Get all active bookings for a specific office
     */
    List<BookingResponseDTO> getBookingsByOffice(String officeName);
}
