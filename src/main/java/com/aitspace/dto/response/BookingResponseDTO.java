package com.aitspace.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDTO {

    private String employeeId;

    private String type;
    private String roomName;
    private String floor;
    private Integer capacity;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private String amenities;
    private String status;
    private String officeName;
    private Boolean reminderSent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private String attendees;
    private String email;
    private String bookingIdString;
}
