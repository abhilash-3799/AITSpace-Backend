package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.response.LoginResponseDTO;
import com.ait.mrb_fp.entity.UserLogin;

public class LoginMapper {

    private LoginMapper() {}

    public static LoginResponseDTO toResponse(UserLogin u) {

        return LoginResponseDTO.builder()
                .loginId(u.getLoginId())
                .employeeId(u.getEmployee().getEmployeeId())
                .employeeNumber(u.getEmployee().getEmployeeNumber())  // ADD THIS
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
