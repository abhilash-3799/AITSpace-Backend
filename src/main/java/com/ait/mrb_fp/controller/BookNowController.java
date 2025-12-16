package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.BookingRequestDTO;
import com.ait.mrb_fp.dto.response.BookingResponseDTO;
import com.ait.mrb_fp.dto.response.AvailabilityResponseDTO;
import com.ait.mrb_fp.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Book Now API", description = "APIs for meeting room booking system")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:5173"})
public class BookNowController {

    private final BookingService bookingService;

    @Operation(summary = "Book a meeting room",
            description = "Create a new meeting room booking from the Book Now form")
    @PostMapping("/book-now")
    public ResponseEntity<BookingResponseDTO> bookNow(@Valid @RequestBody BookingRequestDTO bookingRequest) {
        log.info("Received booking request for room: {} on {}",
                bookingRequest.getRoomName(), bookingRequest.getDate());
        BookingResponseDTO response = bookingService.createBooking(bookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all bookings",
            description = "Retrieve all meeting room bookings")
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        log.info("Fetching all bookings");
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @Operation(summary = "Get booking by ID",
            description = "Retrieve a specific booking by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable String id) {
        log.info("Fetching booking with ID: {}", id);
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @Operation(summary = "Get bookings by room and date",
            description = "Get all bookings for a specific room on a given date")
    @GetMapping("/room/{roomName}/date/{date}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByRoomAndDate(
            @PathVariable String roomName,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Fetching bookings for room: {} on date: {}", roomName, date);
        return ResponseEntity.ok(bookingService.getBookingsByRoomAndDate(roomName, date));
    }

    @Operation(summary = "Check room availability",
            description = "Check if a room is available for the requested time slot")
    @PostMapping("/check-availability")
    public ResponseEntity<Boolean> checkAvailability(@Valid @RequestBody BookingRequestDTO bookingRequest) {
        log.info("Checking availability for room: {} from {} to {} on {}",
                bookingRequest.getRoomName(), bookingRequest.getStartTime(),
                bookingRequest.getEndTime(), bookingRequest.getDate());
        boolean isAvailable = bookingService.checkAvailability(bookingRequest);
        return ResponseEntity.ok(isAvailable);
    }

    @Operation(summary = "Get room availability percentage",
            description = "Get the percentage of time a room is booked for today")
    @GetMapping("/room/{roomName}/availability")
    public ResponseEntity<AvailabilityResponseDTO> getRoomAvailability(
            @PathVariable String roomName) {
        log.info("Getting availability for room: {}", roomName);
        LocalDate today = LocalDate.now();
        AvailabilityResponseDTO availability = bookingService.getRoomAvailability(roomName, today);
        return ResponseEntity.ok(availability);
    }

    @Operation(summary = "Get room availability for specific date",
            description = "Get the percentage of time a room is booked for a specific date")
    @GetMapping("/room/{roomName}/availability/{date}")
    public ResponseEntity<AvailabilityResponseDTO> getRoomAvailabilityForDate(
            @PathVariable String roomName,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Getting availability for room: {} on date: {}", roomName, date);
        AvailabilityResponseDTO availability = bookingService.getRoomAvailability(roomName, date);
        return ResponseEntity.ok(availability);
    }

    @Operation(summary = "Cancel a booking",
            description = "Cancel an existing meeting room booking")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable String id) {
        log.info("Cancelling booking with ID: {}", id);
        BookingResponseDTO cancelledBooking = bookingService.cancelBooking(id);
        return ResponseEntity.ok(cancelledBooking);
    }

    @Operation(summary = "Get bookings by office",
            description = "Get all active bookings for a specific office")
    @GetMapping("/office/{officeName}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByOffice(@PathVariable String officeName) {
        log.info("Fetching bookings for office: {}", officeName);
        return ResponseEntity.ok(bookingService.getBookingsByOffice(officeName));
    }

    @Operation(summary = "Health check",
            description = "Check if the booking service is running")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Booking service is running");
    }
}