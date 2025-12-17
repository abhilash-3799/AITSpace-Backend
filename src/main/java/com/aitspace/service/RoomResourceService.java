package com.aitspace.service;

import com.aitspace.dto.request.RoomResourceRequestDto;
import com.aitspace.dto.response.RoomResourceResponseDto;

import java.util.List;

public interface RoomResourceService {
    RoomResourceResponseDto create(RoomResourceRequestDto dto);
    List<RoomResourceResponseDto> getByWorkspace(Long workspaceId);
    RoomResourceResponseDto update(Long id, RoomResourceRequestDto dto);
    void softDelete(Long id);
}

