package com.aitspace.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookingResponseDTO {
    private String allocationId;
    private String seatNumber;
    private String employeeName;
    private LocalDateTime allocationDate;
    private String status;
    private boolean isActive;

    public void setMessage(String s)
    {
    }
}
