package com.ait.mrb_fp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomResponseDTO {
    private String roomId;
    private String roomName;
    private String officeName;
    private int capacity;
    private String roomType;
    private boolean isActive;
}
