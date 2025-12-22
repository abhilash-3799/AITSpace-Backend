package com.aitspace.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_booking_id", columnNames = {"booking_id_string"})
                // Removed unique constraint on email
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    /* =========================
       FOREIGN KEY → EMPLOYEE
       ========================= */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "employeeId",
            nullable = false
    )
    private Employee employee;

    /* =========================
       EXISTING FIELDS (UNCHANGED)
       ========================= */
    @Column(name = "type", nullable = false)
    private String type = "meeting";

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "floor", nullable = false)
    private String floor;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "booking_date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    /* =========================
       AMENITIES (SAME COLUMN)
       ========================= */
    @Column(name = "amenities", columnDefinition = "TEXT")
    private String amenities;

    @Column(name = "status")
    private String status = "Active";

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "reminder_sent")
    private Boolean reminderSent = false;

    @Column(name = "attendees")
    private String attendees;

    @Column(name = "email", nullable = false)

    private String email;

    @Id
    @Column(name = "booking_id_string", unique = true, nullable = false)
    private String bookingIdString;

    /* =========================
       AUDIT FIELDS
       ========================= */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /* =========================
       ENTITY LIFECYCLE
       ========================= */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.bookingIdString == null) {
            this.bookingIdString = "BK-" + System.currentTimeMillis();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
