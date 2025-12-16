package com.ait.mrb_fp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponseDTO {
    private String roomName;
    private Integer bookedPercentage;
    private Integer availablePercentage;
    private String status;
}