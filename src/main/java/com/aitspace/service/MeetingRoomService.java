package com.aitspace.service;

import com.aitspace.dto.request.MeetingRoomRequestDTO;
import com.aitspace.dto.response.MeetingRoomResponseDTO;

import java.util.List;

public interface MeetingRoomService {
    List<MeetingRoomResponseDTO> getAll();

    MeetingRoomResponseDTO getById(String id);

    MeetingRoomResponseDTO create(MeetingRoomRequestDTO request);

    MeetingRoomResponseDTO update(String id, MeetingRoomRequestDTO request);

    void delete(String id);
}
