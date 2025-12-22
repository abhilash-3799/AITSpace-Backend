package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "queue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"office", "seats"})
@EqualsAndHashCode(exclude = {"office", "seats"})
public class Queue {

    @Id

    @Column(length = 45, nullable = false)
    private String queueId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;
    @Column(length = 50, nullable = false, unique = true)
    private String queueName;
    @Column(nullable = false)
    private int totalSeats;
    @Column(nullable = false)
    private boolean isActive = true;
    @OneToMany(mappedBy = "queue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    @PrePersist
    public void generateId() {
        if (this.queueId == null || this.queueId.isBlank()) {

            this.queueId = "QUEUE-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }
}

