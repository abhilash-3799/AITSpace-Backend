package com.aitspace.mapper;

import com.aitspace.dto.request.SignupRequestDTO;
import com.aitspace.dto.response.SignupResponseDTO;
import com.aitspace.entity.Employee;
import com.aitspace.entity.UserLogin;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SignupMapper {

    private SignupMapper() {}

    public static UserLogin toEntity(SignupRequestDTO r, Employee e, PasswordEncoder encoder) {
        UserLogin u = new UserLogin();
        u.setEmployee(e);
        u.setUsername(r.getUsername());
        u.setEmail(r.getEmail().toLowerCase().trim());
        u.setPassword(encoder.encode(r.getPassword()));
        u.setRole(UserLogin.Role.valueOf(r.getRole().toUpperCase()));
        u.setActive(true);
        return u;
    }

    public static SignupResponseDTO toResponse(UserLogin u, String message, boolean success) {
        return SignupResponseDTO.builder()
                .success(success)
                .message(message)
                .loginId(u.getLoginId())
                .username(u.getUsername())
                .email(u.getEmail())
                .employeeName(u.getEmployee() != null
                        ? u.getEmployee().getFirstName() + " " + u.getEmployee().getLastName()
                        : "N/A")
                .role(u.getRole().name())
                .build();
    }
}