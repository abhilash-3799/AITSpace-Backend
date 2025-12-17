package com.aitspace.controller;

import com.aitspace.dto.request.OfficeRequestDTO;
import com.aitspace.dto.response.OfficeResponseDTO;
import com.aitspace.service.OfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/office")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Office Controller", description = "APIs for managing office information")
public class OfficeController {

    private final OfficeService officeService;

    @Operation(summary = "Create new office", description = "Creates a new office record")
    @PostMapping
    public ResponseEntity<OfficeResponseDTO> createOffice(@Valid @RequestBody OfficeRequestDTO dto) {
        log.info("Creating new office: {}", dto.getOfficeName());
        return ResponseEntity.ok(officeService.createOffice(dto));
    }

    @Operation(summary = "Get office by ID", description = "Fetch an office using its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<OfficeResponseDTO> getOfficeById(@PathVariable String id) {
        log.info("Fetching office with ID: {}", id);
        return ResponseEntity.ok(officeService.getOfficeById(id));
    }

    @Operation(summary = "Get all offices", description = "Retrieve a list of all active offices")
    @GetMapping
    public ResponseEntity<List<OfficeResponseDTO>> getAllOffices() {
        log.info("Fetching all offices");
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @Operation(summary = "Update office details", description = "Updates an existing office record")
    @PutMapping("/{id}")
    public ResponseEntity<OfficeResponseDTO> updateOffice(@PathVariable String id, @RequestBody OfficeRequestDTO dto) {
        log.info("Updating office with ID: {}", id);
        return ResponseEntity.ok(officeService.updateOffice(id, dto));
    }

    @Operation(summary = "Deactivate office", description = "Soft deletes (deactivates) an office record")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateOffice(@PathVariable String id) {
        log.warn("Deactivating office with ID: {}", id);
        officeService.deactivateOffice(id);
        return ResponseEntity.ok("Office marked as inactive (soft deleted).");
    }
}
