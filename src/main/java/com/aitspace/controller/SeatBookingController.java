package com.aitspace.controller;

import com.aitspace.dto.request.SeatBookingRequestDTO;
import com.aitspace.dto.response.SeatBookingResponseDTO;
import com.aitspace.service.SeatBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-booking")
@Slf4j
@Tag(name = "Seat Booking Controller", description = "Endpoints for managing seat bookings")
public class SeatBookingController {

    private final SeatBookingService seatBookingService;

    public SeatBookingController(SeatBookingService seatBookingService) {
        this.seatBookingService = seatBookingService;
    }

    @Operation(summary = "Create Seat Booking", description = "Creates a new seat booking for an employee")
    @PostMapping
    public SeatBookingResponseDTO create(@Valid @RequestBody SeatBookingRequestDTO dto) {
        log.info("Creating seat booking for employee ID: {}", dto.getEmployeeId());
        return seatBookingService.create(dto);
    }

    @Operation(summary = "Get All Bookings", description = "Fetch all seat bookings")
    @GetMapping
    public List<SeatBookingResponseDTO> getAll() {
        log.info("Fetching all seat bookings");
        return seatBookingService.getAll();
    }

    @Operation(summary = "Get Booking by ID", description = "Fetch a specific seat booking by ID")
    @GetMapping("/{id}")
    public SeatBookingResponseDTO getById(@PathVariable String id) {
        log.info("Fetching seat booking with ID: {}", id);
        return seatBookingService.getById(id);
    }

    @Operation(summary = "Update Seat Booking", description = "Update an existing seat booking by ID")
    @PutMapping("/{id}")
    public SeatBookingResponseDTO update(@PathVariable String id, @RequestBody SeatBookingRequestDTO dto) {
        log.info("Updating seat booking with ID: {}", id);
        return seatBookingService.update(id, dto);
    }

    @Operation(summary = "Delete Seat Booking", description = "Delete a seat booking by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.warn("Deleting seat booking with ID: {}", id);
        seatBookingService.delete(id);
    }

    @Operation(summary = "Bulk Booking", description = "Perform bulk seat bookings by team lead")
    @PostMapping("/bulk/{teamLeadId}")
    public List<SeatBookingResponseDTO> createBulk(@PathVariable String teamLeadId,
                                                   @RequestBody List<SeatBookingRequestDTO> dtos) {
        log.info("Performing bulk seat booking by team lead ID: {}", teamLeadId);
        return seatBookingService.createBulk(dtos, teamLeadId);
    }
}
