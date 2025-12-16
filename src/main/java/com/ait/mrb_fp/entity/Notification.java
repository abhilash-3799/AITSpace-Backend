package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employee")
@EqualsAndHashCode(exclude = "employee")
public class Notification {

    @Id
    @Column(length = 45, nullable = false)
    private String notificationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @Column(length = 150, nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private boolean isActive = true;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    public void generateId() {
        if (this.notificationId == null || this.notificationId.isBlank()) {

            this.notificationId = "N-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }
}

