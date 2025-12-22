package com.ait.mrb_fp.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomBookingResponseDTO {
    private String meetingId;
    private String roomName;
    private String bookedBy;
    private LocalDate meetingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private String status;
    private boolean isActive;
}
