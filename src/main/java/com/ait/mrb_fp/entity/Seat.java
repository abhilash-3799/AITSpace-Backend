package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"office", "queue", "assignedTeam"})
@EqualsAndHashCode(exclude = {"office", "queue", "assignedTeam"})
public class Seat {

    @Id
    @Column(length = 45, nullable = false)
    private String seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @Column(length = 50, nullable = false, unique = true)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private SeatStatus seatStatus = SeatStatus.UNALLOCATED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_team_id")
    private Team assignedTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_id")
    private Queue queue;

    @Column(nullable = false)
    private boolean isAvailable = true;

    @Column(nullable = false)
    private boolean isActive = true;

    @PrePersist
    public void generateId() {
        if (this.seatId == null || this.seatId.isBlank()) {

            this.seatId = "SEAT-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }

    public enum SeatStatus {
        UNALLOCATED, ALLOCATED
    }
}

