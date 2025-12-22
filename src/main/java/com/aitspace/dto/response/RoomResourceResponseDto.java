package com.aitspace.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class RoomResourceResponseDto {
    private Long id;
    private String name;
    private Integer floor;
    private String capacity;
    private String description;
    private List<String> amenities;
    private Integer availabilityPercentage;
    private Boolean isActive;
}

