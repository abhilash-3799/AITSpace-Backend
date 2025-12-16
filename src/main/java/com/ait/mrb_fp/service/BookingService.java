package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.BookingRequestDTO;
import com.ait.mrb_fp.dto.response.BookingResponseDTO;
import com.ait.mrb_fp.dto.response.AvailabilityResponseDTO;

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