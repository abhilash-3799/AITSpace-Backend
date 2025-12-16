package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

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

    @ElementCollection
    @CollectionTable(name = "booking_amenities",
            joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @Column(name = "status")
    private String status = "Active";

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "reminder_sent")
    private Boolean reminderSent = false;

    @Column(name = "booked_at")
    private LocalDateTime bookedAt;

    @Column(name = "attendees")
    private String attendees;

    @Column(name = "email")
    private String email;

    @Column(name = "booking_id_string")
    private String bookingIdString;

    @PrePersist
    protected void onCreate() {
        bookedAt = LocalDateTime.now();
        if (bookingIdString == null) {
            bookingIdString = "BK-" + System.currentTimeMillis();
        }
    }
}