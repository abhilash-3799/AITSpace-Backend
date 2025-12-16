package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "meeting_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"office", "bookings"})
@EqualsAndHashCode(exclude = {"office", "bookings"})
public class MeetingRoom {

    @Id
    @Column(length = 45, nullable = false)
    private String roomId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;
    @Column(length = 100, nullable = false, unique = true)
    private String roomName;
    @Column(nullable = false)
    private int capacity;
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private RoomType roomType;
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MeetingRoomStatus roomStatus = MeetingRoomStatus.AVAILABLE;
    @Column(nullable = false)
    private boolean isActive = true;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingRoomBooking> bookings;

    @PrePersist
    public void generateId() {
        if (this.roomId == null || this.roomId.isBlank()) {

            this.roomId = "MR-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }

    public enum RoomType {
        BOARD_ROOM, CONFERENCE_ROOM, CABIN
    }

    public enum MeetingRoomStatus {
        AVAILABLE, NOT_AVAILABLE
    }
}


