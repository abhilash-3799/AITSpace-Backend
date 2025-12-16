package com.ait.mrb_fp.dto.request;

import com.ait.mrb_fp.entity.SeatBooking;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookingRequestDTO {

    @NotBlank(message = "Seat ID is required")
    @Size(max = 50, message = "Seat ID must not exceed 50 characters")
    private String seatId;

    @NotBlank(message = "Employee ID is required")
    @Size(max = 50, message = "Employee ID must not exceed 50 characters")
    private String employeeId;

    @NotNull(message = "Seat booking date is required")
    @FutureOrPresent(message = "Booking date cannot be in the past")
    private LocalDateTime seatBookingDate;

    private SeatBooking.BookingStatus status;
}
