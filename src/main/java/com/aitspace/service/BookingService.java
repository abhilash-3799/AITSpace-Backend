package com.aitspace.service;

import com.aitspace.dto.request.BookingRequestDTO;
import com.aitspace.dto.response.BookingResponseDTO;
import com.aitspace.dto.response.AvailabilityResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO bookingRequest);

    List<BookingResponseDTO> getAllBookings();

    BookingResponseDTO getBookingById(String id);

    List<BookingResponseDTO> getBookingsByRoomAndDate(String roomName, LocalDate date);

    AvailabilityResponseDTO getRoomAvailability(String roomName, LocalDate date);

    BookingResponseDTO cancelBooking(String id);

    Boolean checkAvailability(BookingRequestDTO bookingRequest);

    List<BookingResponseDTO> getBookingsByOffice(String officeName);
}