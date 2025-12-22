package com.aitspace.service;

import com.aitspace.dto.request.RoomResourceRequestDto;
import com.aitspace.dto.response.RoomResourceResponseDto;
import com.aitspace.entity.RoomResource;
import com.aitspace.entity.Workspace;
import com.aitspace.mapper.RoomResourceMapper;
import com.aitspace.repository.RoomResourceRepository;
import com.aitspace.repository.WorkspaceRepository;
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

