package com.aitspace.controller;

import com.aitspace.dto.request.SeatBookingRequestDTO;
import com.aitspace.dto.response.SeatBookingResponseDTO;
import com.aitspace.service.SeatBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-booking")
@RequiredArgsConstructor
public class SeatBookingController {

    private final SeatBookingService seatBookingService;

    @PostMapping
    public ResponseEntity<SeatBookingResponseDTO> createBooking(
            @Valid @RequestBody SeatBookingRequestDTO requestDTO) {
        SeatBookingResponseDTO response = seatBookingService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SeatBookingResponseDTO>> getAllBookings() {
        List<SeatBookingResponseDTO> bookings = seatBookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<SeatBookingResponseDTO> getBookingById(@PathVariable String bookingId) {
        SeatBookingResponseDTO booking = seatBookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<SeatBookingResponseDTO> updateBooking(
            @PathVariable String bookingId,
            @Valid @RequestBody SeatBookingRequestDTO requestDTO) {
        SeatBookingResponseDTO updatedBooking = seatBookingService.updateBooking(bookingId, requestDTO);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable String bookingId) {
        seatBookingService.cancel(bookingId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<SeatBookingResponseDTO>> createBulkBooking(
            @Valid @RequestBody List<SeatBookingRequestDTO> requestDTOs) {
        List<SeatBookingResponseDTO> responses = seatBookingService.createBulkBooking(requestDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }
}