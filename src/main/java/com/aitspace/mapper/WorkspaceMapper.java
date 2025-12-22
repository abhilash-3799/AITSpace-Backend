package com.aitspace.mapper;

import com.aitspace.dto.request.WorkspaceRequestDto;
import com.aitspace.dto.response.WorkspaceResponseDto;
import com.aitspace.entity.Workspace;


public class WorkspaceMapper {

    public static Workspace toEntity(WorkspaceRequestDto dto) {
        return Workspace.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .numberOfFloors(dto.getNumberOfFloors())
                .totalRooms(dto.getTotalRooms())
                .description(dto.getDescription())
                .isDeleted(false)
                .build();
    }

    public static WorkspaceResponseDto toDto(Workspace entity) {
        return WorkspaceResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .numberOfFloors(entity.getNumberOfFloors())
                .totalRooms(entity.getTotalRooms())
                .description(entity.getDescription())

                .build();
    }
}

