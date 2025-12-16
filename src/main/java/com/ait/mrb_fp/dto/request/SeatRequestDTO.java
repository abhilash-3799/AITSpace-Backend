package com.ait.mrb_fp.dto.request;

import com.ait.mrb_fp.entity.Seat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequestDTO {

    @NotBlank(message = "Office ID is required")
    @Size(max = 50, message = "Office ID must not exceed 50 characters")
    private String officeId;

    @NotBlank(message = "Seat number is required")
    @Size(max = 20, message = "Seat number must not exceed 20 characters")
    private String seatNumber;

    @NotBlank(message = "Team ID is required")
    @Size(max = 50, message = "Team ID must not exceed 50 characters")
    private String assignedTeamId;

    @NotBlank(message = "Queue ID is required")
    @Size(max = 50, message = "Queue ID must not exceed 50 characters")
    private String queueId;

    @NotNull(message = "Seat status is required")
    private Seat.SeatStatus seatStatus;

    private boolean available;
}
