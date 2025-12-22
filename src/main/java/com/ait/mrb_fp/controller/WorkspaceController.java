package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.WorkspaceRequestDto;
import com.ait.mrb_fp.dto.response.WorkspaceResponseDto;
import com.ait.mrb_fp.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspace")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:5173"})
public class WorkspaceController {

    private final WorkspaceService service;

    @PostMapping
    public WorkspaceResponseDto create(@RequestBody WorkspaceRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<WorkspaceResponseDto> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public WorkspaceResponseDto update(@PathVariable Long id, @RequestBody WorkspaceRequestDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.softDelete(id);
    }
}

