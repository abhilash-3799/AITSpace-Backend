package com.ait.mrb_fp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatResponseDTO {
    private String seatId;
    private String seatNumber;
    private String officeName;
    private String teamName;
    private String queueName;
    private String seatStatus;
    private boolean isAvailable;
    private boolean isActive;
}
