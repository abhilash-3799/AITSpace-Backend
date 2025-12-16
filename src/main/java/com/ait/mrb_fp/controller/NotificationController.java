package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.request.SeatCancellationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @Operation(summary = "Get notifications by type",
            description = "Retrieve notifications by their type. Valid types: GENERAL, SEAT_CANCELLATION, SEAT_ASSIGNMENT, SYSTEM_ALERT")
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getByType(@PathVariable String type) {
        log.info("Fetching notifications by type: {}", type);
        try {
            List<NotificationResponseDTO> notifications = notificationService.getByType(type);
            return ResponseEntity.ok(notifications);
        } catch (RuntimeException ex) {
            log.error("Error fetching notifications by type {}: {}", type, ex.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error", "Invalid request",
                            "message", ex.getMessage(),
                            "timestamp", LocalDateTime.now()
                    ));
        }
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

@Operation(summary = "Create seat cancellation",
        description = "Create a notification for seat cancellation")
@PostMapping("/seat-cancellation/create")
public NotificationResponseDTO createSeatCancellation(
        @Valid @RequestBody SeatCancellationRequestDTO request) {

    log.info("Creating seat cancellation for employee: {}, seat: {}",
            request.getEmployeeId(), request.getSeatNumber());
    return notificationService.createSeatCancellation(request);
}

    @Operation(summary = "Get all seat cancellations",
            description = "Retrieve all seat cancellation notifications")
    @GetMapping("/seat-cancellation")
    public List<NotificationResponseDTO> getAllSeatCancellations() {
        log.info("Fetching all seat cancellation notifications");
        return notificationService.getSeatCancellationNotifications();
    }

    @Operation(summary = "Get seat cancellations by employee",
            description = "Retrieve seat cancellation notifications for a specific employee")
    @GetMapping("/seat-cancellation/employee/{employeeId}")
    public List<NotificationResponseDTO> getSeatCancellationsByEmployee(
            @PathVariable String employeeId) {

        log.info("Fetching seat cancellations for employee ID: {}", employeeId);
        return notificationService.getSeatCancellationsByEmployee(employeeId);
    }

    @Operation(summary = "Cancel seat cancellation",
            description = "Deactivate a seat cancellation notification")
    @PutMapping("/seat-cancellation/{notificationId}/cancel")
    public NotificationResponseDTO cancelSeatCancellation(
            @PathVariable String notificationId) {

        log.info("Cancelling seat cancellation with ID: {}", notificationId);
        return notificationService.cancelSeatCancellation(notificationId);
    }

    @Operation(summary = "Get seat cancellation by ID",
            description = "Retrieve a specific seat cancellation notification")
    @GetMapping("/seat-cancellation/{id}")
    public NotificationResponseDTO getSeatCancellationById(@PathVariable String id) {
        log.info("Fetching seat cancellation with ID: {}", id);
        return notificationService.getById(id); // This will return the notification with all details
    }

}
