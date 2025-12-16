package com.ait.mrb_fp.dto.request;

import com.ait.mrb_fp.entity.MeetingRoom;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomRequestDTO {

    @NotBlank(message = "Office ID is required")
    private String officeId;

    @NotBlank(message = "Room name is required")
    @Size(max = 100, message = "Room name must be less than 100 characters")
    private String roomName;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Room type required")
    private MeetingRoom.RoomType roomType;

}
