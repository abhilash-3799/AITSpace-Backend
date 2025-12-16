package com.ait.mrb_fp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"team", "office"})
@EqualsAndHashCode(exclude = {"team", "office"})
public class Employee {

    @Id
    @Column(length = 45, nullable = false)

    private String employeeId;
    @Column(length = 50, nullable = false, unique = true)
    private String employeeNumber;
    @Column(length = 100, nullable = false)
    private String firstName;
    @Column(length = 100, nullable = false)
    private String lastName;
    @Column(length = 150, nullable = false, unique = true)
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private EmployeeType employeeType = EmployeeType.REGULAR;
    @Column(nullable = false)
    private boolean teamLead = false;
    @Column(nullable = false)
    private boolean isActive = true;

    @PrePersist
    public void generateId() {
        if (this.employeeId == null || this.employeeId.isBlank()) {

            this.employeeId = "EMP-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }

    public enum EmployeeType {
        REGULAR, HYBRID, WFH
    }
}