package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.WorkspaceRequestDto;
import com.ait.mrb_fp.dto.response.WorkspaceResponseDto;
import com.ait.mrb_fp.entity.RoomResource;
import com.ait.mrb_fp.entity.Workspace;

import java.util.List;


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

