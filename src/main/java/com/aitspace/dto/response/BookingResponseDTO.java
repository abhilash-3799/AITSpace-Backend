package com.aitspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    private String id;
    private String type;
    private String roomName;
    private String floor;
    private Integer capacity;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<String> amenities;
    private String status;
    private String officeName;
    private Boolean reminderSent;
    private LocalDateTime bookedAt;
    private String attendees;
    private String email;
    private String bookingIdString;
}