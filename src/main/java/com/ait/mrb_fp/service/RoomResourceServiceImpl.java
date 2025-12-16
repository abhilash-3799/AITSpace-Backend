package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.RoomResourceRequestDto;
import com.ait.mrb_fp.dto.response.RoomResourceResponseDto;
import com.ait.mrb_fp.entity.RoomResource;
import com.ait.mrb_fp.entity.Workspace;
import com.ait.mrb_fp.mapper.RoomResourceMapper;
import com.ait.mrb_fp.repository.RoomResourceRepository;
import com.ait.mrb_fp.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomResourceServiceImpl implements RoomResourceService {

    private final RoomResourceRepository roomRepo;
    private final WorkspaceRepository workspaceRepo;

    @Override
    public RoomResourceResponseDto create(RoomResourceRequestDto dto) {
        Workspace workspace = workspaceRepo.findById(dto.getWorkspaceId()).orElseThrow();

        RoomResource room = RoomResource.builder()
                .name(dto.getName())
                .floor(dto.getFloor())
                .capacity(dto.getCapacity())
                .description(dto.getDescription())
                .amenities(dto.getAmenities())
                .availabilityPercentage(dto.getAvailabilityPercentage())
                .isActive(dto.getIsActive())
                .isDeleted(false)
                .workspace(workspace)
                .build();

        return RoomResourceMapper.toDto(roomRepo.save(room));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResourceResponseDto> getByWorkspace(Long workspaceId) {
        return roomRepo.findByWorkspaceWithAmenities(workspaceId)
                .stream()
                .map(RoomResourceMapper::toDto)
                .toList();
    }

    @Override
    public RoomResourceResponseDto update(Long id, RoomResourceRequestDto dto) {
        RoomResource room = roomRepo.findById(id).orElseThrow();
        room.setName(dto.getName());
        room.setFloor(dto.getFloor());
        room.setCapacity(dto.getCapacity());
        room.setDescription(dto.getDescription());
        room.setAmenities(dto.getAmenities());
        room.setAvailabilityPercentage(dto.getAvailabilityPercentage());
        room.setIsActive(dto.getIsActive());
        return RoomResourceMapper.toDto(roomRepo.save(room));
    }

    @Override
    public void softDelete(Long id) {
        RoomResource room = roomRepo.findById(id).orElseThrow();
        room.setIsDeleted(true);
        roomRepo.save(room);
    }
}

