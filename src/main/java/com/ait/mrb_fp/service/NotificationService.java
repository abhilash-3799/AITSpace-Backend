package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.request.SeatCancellationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {
    List<NotificationResponseDTO> getAll();

    NotificationResponseDTO getById(String id);

    NotificationResponseDTO create(NotificationRequestDTO request);

    NotificationResponseDTO update(String id, NotificationRequestDTO request);

    void delete(String id);





    public List<NotificationResponseDTO> getByType(String type);


    NotificationResponseDTO createSeatCancellation(SeatCancellationRequestDTO request);
    List<NotificationResponseDTO> getSeatCancellationNotifications();
    List<NotificationResponseDTO> getSeatCancellationsByEmployee(String employeeId);
    NotificationResponseDTO cancelSeatCancellation(String notificationId);
}
