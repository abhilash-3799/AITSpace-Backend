package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "seat_booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"seat", "employee"})
@EqualsAndHashCode(exclude = {"seat", "employee"})
public class SeatBooking {

    @Id
    @Column(length = 45, nullable = false)
    private String seatBookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDateTime seatBookingDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private BookingStatus status = BookingStatus.ALLOCATED;

    @Column(nullable = false)
    private boolean isActive = true;

    @PrePersist
    public void generateId() {
        if (this.seatBookingId == null || this.seatBookingId.isBlank()) {

            this.seatBookingId = "SB-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }

    public enum BookingStatus {
        ALLOCATED, RELEASED
    }
}


