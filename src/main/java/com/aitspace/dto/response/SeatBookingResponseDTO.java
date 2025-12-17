package com.aitspace.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookingResponseDTO {
    private String seatBookingId;
    private String seatNumber;
    private String employeeName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String status;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
