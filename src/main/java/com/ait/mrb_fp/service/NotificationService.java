package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {
    List<NotificationResponseDTO> getAll();

    NotificationResponseDTO getById(String id);

    NotificationResponseDTO create(NotificationRequestDTO request);

    NotificationResponseDTO update(String id, NotificationRequestDTO request);

    void delete(String id);
}
