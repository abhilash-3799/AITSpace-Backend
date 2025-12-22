package com.aitspace.controller;

import com.aitspace.dto.request.MeetingRoomRequestDTO;
import com.aitspace.dto.response.MeetingRoomResponseDTO;
import com.aitspace.service.MeetingRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting-room")
@Slf4j
@Tag(name = "Meeting Room Controller", description = "APIs for managing meeting rooms")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @Operation(summary = "Get all meeting rooms", description = "Retrieve all available meeting rooms")
    @GetMapping
    public List<MeetingRoomResponseDTO> getAll() {
        log.info("Fetching all meeting rooms");
        return meetingRoomService.getAll();
    }

    @Operation(summary = "Get meeting room by ID", description = "Retrieve a meeting room by its ID")
    @GetMapping("/{id}")
    public MeetingRoomResponseDTO getById(@PathVariable String id) {
        log.info("Fetching meeting room with ID: {}", id);
        return meetingRoomService.getById(id);
    }

    @Operation(summary = "Create a new meeting room", description = "Add a new meeting room")
    @PostMapping
    public MeetingRoomResponseDTO create(@Valid @RequestBody MeetingRoomRequestDTO request) {
        log.info("Creating new meeting room for office ID: {}", request.getOfficeId());
        return meetingRoomService.create(request);
    }

    @Operation(summary = "Update a meeting room", description = "Update an existing meeting room by ID")
    @PutMapping("/{id}")
    public MeetingRoomResponseDTO update(@PathVariable String id, @RequestBody MeetingRoomRequestDTO request) {
        log.info("Updating meeting room with ID: {}", id);
        return meetingRoomService.update(id, request);
    }

    @Operation(summary = "Delete a meeting room", description = "Delete a meeting room by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.warn("Deleting meeting room with ID: {}", id);
        meetingRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
