package com.aitspace.dto.request;

import com.aitspace.entity.SeatBooking;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomBookingRequestDTO {

    @NotBlank(message = "Employee ID is required")
    private String bookedByEmployeeId;

    @NotBlank(message = "Room ID is required")
    private String roomId;

    @NotNull(message = "Meeting date is required")
    @FutureOrPresent(message = "Meeting date cannot be in the past")
    private LocalDate meetingDate;

    @NotNull(message = "Start time is required")
    @FutureOrPresent(message = "Start time cannot be in the past")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @NotBlank(message = "Purpose is required")
    @Size(max = 255, message = "Purpose cannot exceed 255 characters")
    private String purpose;

    private SeatBooking.BookingStatus status;

    @AssertTrue(message = "End time must be after start time")
    public boolean isValidTimeRange() {
        return startTime != null && endTime != null && endTime.isAfter(startTime);
    }
}
