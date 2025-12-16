package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.SignupRequestDTO;
import com.ait.mrb_fp.dto.response.SignupResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.UserLogin;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SignupMapper {

    private SignupMapper() {}

    public static UserLogin toEntity(SignupRequestDTO r, Employee e, PasswordEncoder encoder) {
        UserLogin u = new UserLogin();
        u.setEmployee(e);
        u.setUsername(r.getUsername());
        u.setPassword(encoder.encode(r.getPassword()));
        u.setRole(UserLogin.Role.valueOf(r.getRole().toUpperCase()));
        u.setActive(true);
        return u;
    }

    public static SignupResponseDTO toResponse(UserLogin u, String message, boolean success) {
        return SignupResponseDTO.builder()
                .success(success)
                .message(message)
                .username(u.getUsername())
                .role(u.getRole().name())
                .build();
    }
}
