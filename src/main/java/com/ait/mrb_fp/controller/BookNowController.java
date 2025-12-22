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

    /* =========================
       CREATE BOOKING
       ========================= */
    @Operation(
            summary = "Book a meeting room",
            description = "Create a new meeting room booking linked to an employee"
    )
    @PostMapping("/book-now")
    public ResponseEntity<BookingResponseDTO> bookNow(
            @Valid @RequestBody BookingRequestDTO bookingRequest
    ) {
        log.info(
                "Received booking request | employeeId={} | room={} | date={}",
                bookingRequest.getEmployeeId(),
                bookingRequest.getRoomName(),
                bookingRequest.getDate()
        );

        BookingResponseDTO response = bookingService.createBooking(bookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* =========================
       GET ALL BOOKINGS
       ========================= */
    @Operation(
            summary = "Get all bookings",
            description = "Retrieve all meeting room bookings"
    )
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        log.info("Fetching all bookings");
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    /* =========================
       GET BOOKING BY BOOKING ID STRING
       ========================= */
    @Operation(
            summary = "Get booking by bookingIdString",
            description = "Retrieve a specific booking by booking reference ID"
    )
    @GetMapping("/{bookingIdString}")
    public ResponseEntity<BookingResponseDTO> getBookingById(
            @PathVariable String bookingIdString
    ) {
        log.info("Fetching booking with bookingIdString: {}", bookingIdString);
        return ResponseEntity.ok(bookingService.getBookingById(bookingIdString));
    }

    /* =========================
       BOOKINGS BY ROOM & DATE
       ========================= */
    @Operation(
            summary = "Get bookings by room and date",
            description = "Get all bookings for a specific room on a given date"
    )
    @GetMapping("/room/{roomName}/date/{date}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByRoomAndDate(
            @PathVariable String roomName,
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        log.info(
                "Fetching bookings | room={} | date={}",
                roomName,
                date
        );
        return ResponseEntity.ok(
                bookingService.getBookingsByRoomAndDate(roomName, date)
        );
    }

    /* =========================
       CHECK AVAILABILITY
       ========================= */
    @Operation(
            summary = "Check room availability",
            description = "Check if a room is available for the requested time slot"
    )
    @PostMapping("/check-availability")
    public ResponseEntity<Boolean> checkAvailability(
            @Valid @RequestBody BookingRequestDTO bookingRequest
    ) {
        log.info(
                "Checking availability | room={} | {}-{} | date={}",
                bookingRequest.getRoomName(),
                bookingRequest.getStartTime(),
                bookingRequest.getEndTime(),
                bookingRequest.getDate()
        );

        boolean isAvailable = bookingService.checkAvailability(bookingRequest);
        return ResponseEntity.ok(isAvailable);
    }

    /* =========================
       ROOM AVAILABILITY (TODAY)
       ========================= */
    @Operation(
            summary = "Get room availability percentage",
            description = "Get the percentage of time a room is booked for today"
    )
    @GetMapping("/room/{roomName}/availability")
    public ResponseEntity<AvailabilityResponseDTO> getRoomAvailability(
            @PathVariable String roomName
    ) {
        log.info("Getting availability for room: {}", roomName);
        AvailabilityResponseDTO availability =
                bookingService.getRoomAvailability(roomName, LocalDate.now());
        return ResponseEntity.ok(availability);
    }

    /* =========================
       ROOM AVAILABILITY (DATE)
       ========================= */
    @Operation(
            summary = "Get room availability for specific date",
            description = "Get the percentage of time a room is booked for a specific date"
    )
    @GetMapping("/room/{roomName}/availability/{date}")
    public ResponseEntity<AvailabilityResponseDTO> getRoomAvailabilityForDate(
            @PathVariable String roomName,
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        log.info(
                "Getting availability | room={} | date={}",
                roomName,
                date
        );
        return ResponseEntity.ok(
                bookingService.getRoomAvailability(roomName, date)
        );
    }

    /* =========================
       CANCEL BOOKING (SOFT DELETE)
       ========================= */
    @Operation(
            summary = "Cancel a booking",
            description = "Cancel an existing meeting room booking"
    )
    @PutMapping("/{bookingIdString}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(
            @PathVariable String bookingIdString
    ) {
        log.info("Cancelling booking: {}", bookingIdString);
        return ResponseEntity.ok(
                bookingService.cancelBooking(bookingIdString)

        );
    }

    /* =========================
       BOOKINGS BY OFFICE
       ========================= */
    @Operation(
            summary = "Get bookings by office",
            description = "Get all active bookings for a specific office"
    )
    @GetMapping("/office/{officeName}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByOffice(
            @PathVariable String officeName
    ) {
        log.info("Fetching bookings for office: {}", officeName);
        return ResponseEntity.ok(
                bookingService.getBookingsByOffice(officeName)
        );
    }

    /* =========================
       HEALTH CHECK
       ========================= */
    @Operation(
            summary = "Health check",
            description = "Check if the booking service is running"
    )
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Booking service is running");
    }
}
