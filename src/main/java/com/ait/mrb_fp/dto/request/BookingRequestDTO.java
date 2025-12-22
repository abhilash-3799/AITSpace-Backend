package com.ait.mrb_fp.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {

    @NotBlank
    private String employeeId;

    @NotBlank
    private String type;

    @NotBlank
    private String roomName;

    @NotBlank
    private String floor;

    private Integer capacity;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    private List<String> amenities;

    private String officeName;

    private String attendees;

    @NotBlank
    @Email
    private String email;

    /* =========================
       TIME CONVERTERS
       ========================= */
    public java.time.LocalTime getStartTimeAsLocalTime() {
        return java.time.LocalTime.parse(startTime);
    }

    public java.time.LocalTime getEndTimeAsLocalTime() {
        return java.time.LocalTime.parse(endTime);
    }
}
