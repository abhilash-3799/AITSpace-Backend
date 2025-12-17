package com.aitspace.service;

import com.aitspace.dto.request.WorkspaceRequestDto;
import com.aitspace.dto.response.WorkspaceResponseDto;

import java.util.List;

public interface WorkspaceService {
    WorkspaceResponseDto create(WorkspaceRequestDto dto);
    List<WorkspaceResponseDto> getAll();
    WorkspaceResponseDto update(Long id, WorkspaceRequestDto dto);
    void softDelete(Long id);
}


