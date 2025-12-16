package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomBookingResponseDTO;

import java.util.List;

public interface MeetingRoomBookingService {
    List<MeetingRoomBookingResponseDTO> getAll();

    MeetingRoomBookingResponseDTO getById(String id);

    MeetingRoomBookingResponseDTO create(MeetingRoomBookingRequestDTO request);

    MeetingRoomBookingResponseDTO update(String id, MeetingRoomBookingRequestDTO request);

    void delete(String id);
}