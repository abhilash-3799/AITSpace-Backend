package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.RoomResourceRequestDto;
import com.ait.mrb_fp.dto.request.WorkspaceRequestDto;
import com.ait.mrb_fp.dto.response.RoomResourceResponseDto;
import com.ait.mrb_fp.dto.response.WorkspaceResponseDto;

import java.util.List;

public interface WorkspaceService {
    WorkspaceResponseDto create(WorkspaceRequestDto dto);
    List<WorkspaceResponseDto> getAll();
    WorkspaceResponseDto update(Long id, WorkspaceRequestDto dto);
    void softDelete(Long id);
}


