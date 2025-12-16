package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employees")
@EqualsAndHashCode(exclude = "employees")
public class Team {

    @Id
    @Column(length = 45, nullable = false)
    private String teamId;
    @Column(length = 100, nullable = false, unique = true)
    private String teamName;
    @Column(length = 100)
    private String department;
    @Column(nullable = false)
    private boolean isActive = true;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    @PrePersist
    public void generateId() {
        if (this.teamId == null || this.teamId.isBlank()) {

            this.teamId = "TEAM-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }
}


