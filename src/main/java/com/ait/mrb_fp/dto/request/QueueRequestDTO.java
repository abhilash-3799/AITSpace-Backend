package com.ait.mrb_fp.dto.request;

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
public class QueueRequestDTO {

    @NotBlank(message = "Office ID is required")
    @Size(max = 50, message = "Office ID must not exceed 50 characters")
    private String officeId;

    @NotBlank(message = "Queue name is required")
    @Size(max = 100, message = "Queue name must not exceed 100 characters")
    private String queueName;

    @NotNull(message = "Total seats in queue is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    private Integer totalSeats;
}
