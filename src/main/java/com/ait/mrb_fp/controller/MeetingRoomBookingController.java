package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrb_fp.service.MeetingRoomBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting-room-booking")
@Slf4j
@Tag(name = "Meeting Room Booking Controller", description = "APIs for managing meeting room bookings")
public class MeetingRoomBookingController {

    private final MeetingRoomBookingService meetingRoomBookingService;

    public MeetingRoomBookingController(MeetingRoomBookingService meetingRoomBookingService) {
        this.meetingRoomBookingService = meetingRoomBookingService;
    }

    @Operation(summary = "Get all meeting room bookings", description = "Retrieve all meeting room bookings")
    @GetMapping
    public List<MeetingRoomBookingResponseDTO> getAll() {
        log.info("Fetching all meeting room bookings");
        return meetingRoomBookingService.getAll();
    }

    @Operation(summary = "Get booking by ID", description = "Retrieve a specific meeting room booking by ID")
    @GetMapping("/{id}")
    public MeetingRoomBookingResponseDTO getById(@PathVariable String id) {
        log.info("Fetching meeting room booking with ID: {}", id);
        return meetingRoomBookingService.getById(id);
    }

    @Operation(summary = "Create a meeting room booking", description = "Book a meeting room")
    @PostMapping
    public MeetingRoomBookingResponseDTO create(@Valid @RequestBody MeetingRoomBookingRequestDTO request) {
        log.info("Creating meeting room booking for employee ID: {}", request.getBookedByEmployeeId());
        return meetingRoomBookingService.create(request);
    }

    @Operation(summary = "Update a meeting room booking", description = "Update an existing meeting room booking")
    @PutMapping("/{id}")
    public MeetingRoomBookingResponseDTO update(@PathVariable String id, @RequestBody MeetingRoomBookingRequestDTO request) {
        log.info("Updating meeting room booking with ID: {}", id);
        return meetingRoomBookingService.update(id, request);
    }

    @Operation(summary = "Delete a meeting room booking", description = "Delete a meeting room booking by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.warn("Deleting meeting room booking with ID: {}", id);
        meetingRoomBookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
