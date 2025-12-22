package com.aitspace.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Entity
@Table(name = "user_login")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employee")
@EqualsAndHashCode(exclude = "employee")
public class UserLogin {

    @Id
    @Column(length = 50)
    private String loginId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false)
    private boolean isActive = true;

    @PrePersist
    public void generateId() {
        if (this.loginId == null) {
            this.loginId = "LOGIN-" + UUID.randomUUID().toString().substring(0, 4);
        }
    }

    public enum Role {
        EMPLOYEE, ADMIN, TEAM_LEAD
    }
}
