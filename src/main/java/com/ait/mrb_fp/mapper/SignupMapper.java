// SignupMapper.java
package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.SignupRequestDTO;
import com.ait.mrb_fp.dto.response.SignupResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.UserLogin;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SignupMapper {

    private SignupMapper() {}

    public static UserLogin toEntity(SignupRequestDTO req, Employee employee, PasswordEncoder encoder) {
        return UserLogin.builder()
                .employee(employee)
                .username(req.getUsername())
                .email(req.getEmail())  // Map email
                .password(encoder.encode(req.getPassword()))
                .role(UserLogin.Role.valueOf(req.getRole()))
                .isActive(true)
                .build();
    }

    public static SignupResponseDTO toResponse(UserLogin user, String message, boolean success) {
        return SignupResponseDTO.builder()
                .success(success)
                .message(message)
                .loginId(user.getLoginId())
                .username(user.getUsername())
                .email(user.getEmail())  // Include email in response
                .role(user.getRole().name())
                .employeeName(user.getEmployee().getFirstName() + " " + user.getEmployee().getLastName())
                .build();
    }
}