package com.aitspace.service;

import com.aitspace.dto.request.SeatBookingRequestDTO;
import com.aitspace.dto.response.SeatBookingResponseDTO;

import java.util.List;

public interface SeatBookingService {
    SeatBookingResponseDTO create(SeatBookingRequestDTO dto);
    void cancel(String bookingId);
    List<SeatBookingResponseDTO> getAllBookings();
    SeatBookingResponseDTO getBookingById(String bookingId);
    SeatBookingResponseDTO updateBooking(String bookingId, SeatBookingRequestDTO dto);
    List<SeatBookingResponseDTO> createBulkBooking(List<SeatBookingRequestDTO> dtos);
}