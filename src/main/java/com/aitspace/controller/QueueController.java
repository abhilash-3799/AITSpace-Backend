package com.aitspace.controller;

import com.aitspace.dto.request.QueueRequestDTO;
import com.aitspace.dto.response.QueueResponseDTO;
import com.aitspace.service.QueueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queue")
@Slf4j
@Tag(name = "Queue Controller", description = "APIs for managing customer queues")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @Operation(summary = "Create Queue", description = "Creates a new queue for a specific office")
    @PostMapping
    public QueueResponseDTO create(@Valid @RequestBody QueueRequestDTO dto) {
        log.info("Creating new queue for office ID: {}", dto.getOfficeId());
        return queueService.create(dto);
    }

    @Operation(summary = "Get All Queues", description = "Fetch all available queues")
    @GetMapping
    public List<QueueResponseDTO> getAll() {
        log.info("Fetching all queues");
        return queueService.getAll();
    }

    @Operation(summary = "Get Queue by ID", description = "Fetch queue details by queue ID")
    @GetMapping("/{id}")
    public QueueResponseDTO getById(@PathVariable String id) {
        log.info("Fetching queue by ID: {}", id);
        return queueService.getById(id);
    }

    @Operation(summary = "Update Queue", description = "Updates an existing queue by ID")
    @PutMapping("/{id}")
    public QueueResponseDTO update(@PathVariable String id, @RequestBody QueueRequestDTO dto) {
        log.info("Updating queue with ID: {}", id);
        return queueService.update(id, dto);
    }

    @Operation(summary = "Delete Queue", description = "Deletes a queue by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.warn("Deleting queue with ID: {}", id);
        queueService.delete(id);
    }
}
