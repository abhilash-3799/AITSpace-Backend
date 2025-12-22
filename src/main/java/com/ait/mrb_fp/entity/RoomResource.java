package com.ait.mrb_fp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "room_resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer floor;
    private String capacity;
    private String description;

    @ElementCollection
    @CollectionTable(name = "room_amenities", joinColumns = @JoinColumn(name = "room_id"))
    private List<String> amenities;

    private Integer availabilityPercentage;
    private Boolean isActive;

    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
