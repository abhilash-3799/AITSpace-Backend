package com.aitspace.service;

import com.aitspace.dto.request.QueueRequestDTO;
import com.aitspace.dto.response.QueueResponseDTO;

import java.util.List;

public interface QueueService {
    QueueResponseDTO create(QueueRequestDTO dto);

    QueueResponseDTO getById(String id);

    List<QueueResponseDTO> getAll();

    QueueResponseDTO update(String id, QueueRequestDTO dto);

    void delete(String id);
}
