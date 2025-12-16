package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.QueueRequestDTO;
import com.ait.mrb_fp.dto.response.QueueResponseDTO;

import java.util.List;

public interface QueueService {
    QueueResponseDTO create(QueueRequestDTO dto);

    QueueResponseDTO getById(String id);

    List<QueueResponseDTO> getAll();

    QueueResponseDTO update(String id, QueueRequestDTO dto);

    void delete(String id);
}
