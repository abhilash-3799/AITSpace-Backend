// LoginMapper.java
package com.aitspace.mapper;

import com.aitspace.dto.response.LoginResponseDTO;
import com.aitspace.entity.UserLogin;

public class LoginMapper {

    private LoginMapper() {}

    public static LoginResponseDTO toResponse(UserLogin u) {
        return LoginResponseDTO.builder()
                .loginId(u.getLoginId())
                .employeeId(u.getEmployee() != null ? u.getEmployee().getEmployeeId() : null)
                .employeeName(u.getEmployee() != null
                        ? u.getEmployee().getFirstName() + " " + u.getEmployee().getLastName()
                        : null)
                .username(u.getUsername())
                .email(u.getEmail())
                .role(u.getRole().name())
                .isActive(u.isActive())
                .build();
    }
}