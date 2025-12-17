package com.aitspace.service;

import com.aitspace.dto.request.MeetingRoomBookingRequestDTO;
import com.aitspace.dto.response.MeetingRoomBookingResponseDTO;

import java.util.List;

public interface MeetingRoomBookingService {
    List<MeetingRoomBookingResponseDTO> getAll();

    MeetingRoomBookingResponseDTO getById(String id);

    MeetingRoomBookingResponseDTO create(MeetingRoomBookingRequestDTO request);

    MeetingRoomBookingResponseDTO update(String id, MeetingRoomBookingRequestDTO request);

    void delete(String id);
}