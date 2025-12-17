package com.aitspace.controller;

import com.aitspace.dto.request.SeatBookingRequestDTO;
import com.aitspace.service.SeatBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seat-booking")
@RequiredArgsConstructor
public class SeatBookingController {

    private final SeatBookingService bookingService;

    @PostMapping
    public ResponseEntity<?> bookseat(@RequestBody @Valid SeatBookingRequestDTO dto) {
        return ResponseEntity.ok(bookingService.create(dto));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> cancel(@PathVariable String bookingId) {
        bookingService.cancel(bookingId);
        return ResponseEntity.ok().build();
    }
}
