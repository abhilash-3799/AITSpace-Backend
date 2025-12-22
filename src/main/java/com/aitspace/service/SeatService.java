package com.aitspace.service;

import com.aitspace.dto.request.SeatRequestDTO;
import com.aitspace.dto.response.SeatResponseDTO;

import java.util.List;

public interface SeatService {
    SeatResponseDTO create(SeatRequestDTO dto);

    SeatResponseDTO getById(String id);

    List<SeatResponseDTO> getAll();

    SeatResponseDTO update(String id, SeatRequestDTO dto);

    void delete(String id);
}
