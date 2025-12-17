package com.aitspace.service;

import com.aitspace.dto.request.SeatBookingRequestDTO;
import com.aitspace.dto.response.SeatBookingResponseDTO;

public interface SeatBookingService {

    SeatBookingResponseDTO create(SeatBookingRequestDTO dto);

    void cancel(String bookingId);
}
