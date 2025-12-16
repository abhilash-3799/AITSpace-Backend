package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.SeatRequestDTO;
import com.ait.mrb_fp.dto.response.SeatResponseDTO;
import com.ait.mrb_fp.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat")
@Slf4j
@Tag(name = "Seat Controller", description = "APIs for managing office seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @Operation(summary = "Create a new seat", description = "Adds a new seat to specific office")
    @PostMapping
    public SeatResponseDTO create(@Valid @RequestBody SeatRequestDTO dto) {
        log.info("Creating new seat for office ID: {}", dto.getOfficeId());
        return seatService.create(dto);
    }

    @Operation(summary = "Get all seats", description = "Retrieve a list of all seats")
    @GetMapping
    public List<SeatResponseDTO> getAll() {
        log.info("Fetching all seats");
        return seatService.getAll();
    }

    @Operation(summary = "Get seat by ID", description = "Retrieve a seat by its ID")
    @GetMapping("/{id}")
    public SeatResponseDTO getById(@PathVariable String id) {
        log.info("Fetching seat with ID: {}", id);
        return seatService.getById(id);
    }

    @Operation(summary = "Update seat by ID", description = "Update an existing seat record")
    @PutMapping("/{id}")
    public SeatResponseDTO update(@PathVariable String id, @RequestBody SeatRequestDTO dto) {
        log.info("Updating seat with ID: {}", id);
        return seatService.update(id, dto);
    }

    @Operation(summary = "Delete seat by ID", description = "Remove a seat")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.warn("Deleting seat with ID: {}", id);
        seatService.delete(id);
    }
}
