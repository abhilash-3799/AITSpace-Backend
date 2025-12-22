package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.RoomResourceRequestDto;
import com.ait.mrb_fp.dto.response.RoomResourceResponseDto;

import java.util.List;

public interface RoomResourceService {
    RoomResourceResponseDto create(RoomResourceRequestDto dto);
    List<RoomResourceResponseDto> getByWorkspace(Long workspaceId);
    RoomResourceResponseDto update(Long id, RoomResourceRequestDto dto);
    void softDelete(Long id);
}

