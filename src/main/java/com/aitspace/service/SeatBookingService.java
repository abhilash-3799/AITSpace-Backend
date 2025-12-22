package com.aitspace.service;

import com.aitspace.dto.request.SeatBookingRequestDTO;
import com.aitspace.dto.response.SeatBookingResponseDTO;

import java.util.List;

public interface SeatBookingService {
    SeatBookingResponseDTO create(SeatBookingRequestDTO dto);

    SeatBookingResponseDTO getById(String id);

    List<SeatBookingResponseDTO> getAll();

    SeatBookingResponseDTO update(String id, SeatBookingRequestDTO dto);

    void delete(String id);

    List<SeatBookingResponseDTO> createBulk(List<SeatBookingRequestDTO> dtos, String teamLeadId);
}
