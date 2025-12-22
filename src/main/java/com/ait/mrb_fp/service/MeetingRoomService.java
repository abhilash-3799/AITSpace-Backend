package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomResponseDTO;

import java.util.List;

public interface MeetingRoomService {
    List<MeetingRoomResponseDTO> getAll();

    MeetingRoomResponseDTO getById(String id);

    MeetingRoomResponseDTO create(MeetingRoomRequestDTO request);

    MeetingRoomResponseDTO update(String id, MeetingRoomRequestDTO request);

    void delete(String id);
}
