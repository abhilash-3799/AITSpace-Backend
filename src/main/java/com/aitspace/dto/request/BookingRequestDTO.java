package com.aitspace.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    @NotBlank(message = "Room name is required")
    private String roomName;

    @NotBlank(message = "Floor is required")
    private String floor;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotBlank(message = "Start time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private String startTime;

    @NotBlank(message = "End time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private String endTime;

    private List<String> amenities;

    @NotBlank(message = "Office name is required")
    private String officeName;

    private String attendees;

    @Email(message = "Email should be valid")
    private String email;

    private String type = "meeting";

    // Helper method to convert String time to LocalTime
    public LocalTime getStartTimeAsLocalTime() {
        return LocalTime.parse(startTime);
    }

    public LocalTime getEndTimeAsLocalTime() {
        return LocalTime.parse(endTime);
    }

    // Custom validation method
    public boolean isValidTimeRange() {
        if (startTime == null || endTime == null) return false;
        try {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);
            return end.isAfter(start);
        } catch (Exception e) {
            return false;
        }
    }
}