package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.SeatRequestDTO;
import com.ait.mrb_fp.dto.response.SeatResponseDTO;

import java.util.List;

public interface SeatService {
    SeatResponseDTO create(SeatRequestDTO dto);

    SeatResponseDTO getById(String id);

    List<SeatResponseDTO> getAll();

    SeatResponseDTO update(String id, SeatRequestDTO dto);

    void delete(String id);
}
