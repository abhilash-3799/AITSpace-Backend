package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
public class WorkspaceRequestDto {
    private String name;
    private String address;
    private Integer numberOfFloors;
    private Integer totalRooms;
    private String description;
}

