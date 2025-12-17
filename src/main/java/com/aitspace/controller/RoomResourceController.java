package com.aitspace.controller;

import com.aitspace.dto.request.RoomResourceRequestDto;
import com.aitspace.dto.response.RoomResourceResponseDto;
import com.aitspace.service.RoomResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:5173"})
public class RoomResourceController {

    private final RoomResourceService service;

    @PostMapping
    public RoomResourceResponseDto create(@RequestBody RoomResourceRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/workspace/{workspaceId}")
    public List<RoomResourceResponseDto> getByWorkspace(@PathVariable Long workspaceId) {
        return service.getByWorkspace(workspaceId);
    }

    @PutMapping("/{id}")
    public RoomResourceResponseDto update(@PathVariable Long id, @RequestBody RoomResourceRequestDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.softDelete(id);
    }
}

