package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.response.RoomResourceResponseDto;
import com.ait.mrb_fp.entity.RoomResource;

public class RoomResourceMapper {

    public static RoomResourceResponseDto toDto(RoomResource room) {
        return RoomResourceResponseDto.builder()
                .id(room.getId())
                .name(room.getName())
                .floor(room.getFloor())
                .capacity(room.getCapacity())
                .description(room.getDescription())
                .amenities(room.getAmenities())
                .availabilityPercentage(room.getAvailabilityPercentage())
                .isActive(room.getIsActive())
                .build();
    }
}

