package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@Slf4j
@Tag(name = "Notification Controller", description = "APIs for managing notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(summary = "Get all notifications", description = "Retrieve all notifications")
    @GetMapping
    public List<NotificationResponseDTO> getAll() {
        log.info("Fetching all notifications");
        return notificationService.getAll();
    }

    @Operation(summary = "Get notification by ID", description = "Retrieve a notification by its ID")
    @GetMapping("/{id}")
    public NotificationResponseDTO getById(@PathVariable String id) {
        log.info("Fetching notification with ID: {}", id);
        return notificationService.getById(id);
    }

    @Operation(summary = "Create a new notification", description = "Create and assign a new notification")
    @PostMapping
    public NotificationResponseDTO create(@Valid @RequestBody NotificationRequestDTO request) {
        log.info("Creating new notification for employee ID: {}", request.getEmployeeId());
        return notificationService.create(request);
    }

    @Operation(summary = "Update notification", description = "Update an existing notification by ID")
    @PutMapping("/{id}")
    public NotificationResponseDTO update(@PathVariable String id, @RequestBody NotificationRequestDTO request) {
        log.info("Updating notification with ID: {}", id);
        return notificationService.update(id, request);
    }

    @Operation(summary = "Delete notification", description = "Delete a notification by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.warn("Deleting notification with ID: {}", id);
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
