package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "meeting_booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"room", "bookedBy"})
@EqualsAndHashCode(exclude = {"room", "bookedBy"})
public class MeetingRoomBooking {

    @Id
    @Column(length = 45, nullable = false)
    private String meetingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private MeetingRoom room;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booked_by_employee_id", nullable = false)
    private Employee bookedBy;
    @Column(nullable = false)
    private LocalDate meetingDate;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;
    @Column(length = 255)
    private String purpose;
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private MeetingStatus status = MeetingStatus.BOOKED;
    @Column(nullable = false)
    private boolean isActive = true;

    @PrePersist
    public void generateId() {
        if (this.meetingId == null || this.meetingId.isBlank()) {

            this.meetingId = "MRB-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }

    public enum MeetingStatus {
        BOOKED, CANCELLED, COMPLETED
    }


}




