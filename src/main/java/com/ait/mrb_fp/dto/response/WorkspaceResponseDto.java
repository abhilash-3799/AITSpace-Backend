package com.ait.mrb_fp.dto.response;

import lombok.*;


import java.util.List;

@Getter
@Setter
@Builder
public class WorkspaceResponseDto {
    private Long id;
    private String name;
    private String address;
    private Integer numberOfFloors;
    private Integer totalRooms;
    private String description;

}

