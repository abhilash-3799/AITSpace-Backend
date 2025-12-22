package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.WorkspaceRequestDto;
import com.ait.mrb_fp.dto.response.WorkspaceResponseDto;
import com.ait.mrb_fp.entity.Workspace;
import com.ait.mrb_fp.exception.DuplicateResourceException;
import com.ait.mrb_fp.mapper.WorkspaceMapper;
import com.ait.mrb_fp.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository repository;

    @Override
    public WorkspaceResponseDto create(WorkspaceRequestDto dto)
    {
        boolean exists = WorkspaceRepository.existsByName(dto.getName());
        if (exists) {
            throw new DuplicateResourceException("Office name already exists: " + dto.getName());
        }

        return WorkspaceMapper.toDto(repository.save(WorkspaceMapper.toEntity(dto)));
    }

    @Override
    public List<WorkspaceResponseDto> getAll() {
        return repository.findByIsDeletedFalse()
                .stream()
                .map(WorkspaceMapper::toDto)
                .toList();
    }

    @Override
    public WorkspaceResponseDto update(Long id, WorkspaceRequestDto dto) {
        Workspace ws = repository.findById(id).orElseThrow();
        ws.setName(dto.getName());
        ws.setAddress(dto.getAddress());
        ws.setNumberOfFloors(dto.getNumberOfFloors());
        ws.setTotalRooms(dto.getTotalRooms());
        ws.setDescription(dto.getDescription());
        return WorkspaceMapper.toDto(repository.save(ws));
    }

    @Override
    public void softDelete(Long id) {
        Workspace ws = repository.findById(id).orElseThrow();
        ws.setIsDeleted(true);
        repository.save(ws);
    }
}

