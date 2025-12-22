package com.aitspace.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class RoomResourceRequestDto {
    private String name;
    private Integer floor;
    private String capacity;
    private String description;
    private List<String> amenities;
    private Integer availabilityPercentage;
    private Boolean isActive;
    private Long workspaceId;
}

