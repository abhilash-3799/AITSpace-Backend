// AuthServiceImpl.java
package com.aitspace.service;

import com.aitspace.dto.request.SignupRequestDTO;
import com.aitspace.dto.request.LoginRequestDTO;
import com.aitspace.dto.response.SignupResponseDTO;
import com.aitspace.dto.response.LoginResponseDTO;
import com.aitspace.entity.UserLogin;
import com.aitspace.mapper.LoginMapper;
import com.aitspace.mapper.SignupMapper;
import com.aitspace.repository.EmployeeRepository;
import com.aitspace.repository.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserLoginRepository userLoginRepo;
    private final EmployeeRepository employeeRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignupResponseDTO signup(SignupRequestDTO req) {

        if (userLoginRepo.existsByUsername(req.getUsername())) {
            return SignupResponseDTO.builder()
                    .success(false)
                    .message("Username already exists")
                    .build();
        }

        if (userLoginRepo.existsByEmail(req.getEmail())) {
            return SignupResponseDTO.builder()
                    .success(false)
                    .message("Email already registered")
                    .build();
        }

        var employee = employeeRepo.findById(req.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        UserLogin user = SignupMapper.toEntity(req, employee, passwordEncoder);
        userLoginRepo.save(user);

        return SignupMapper.toResponse(user, "Signup successful", true);
    }

    @Transactional
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        var user = userLoginRepo.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!user.isActive()) {
            throw new RuntimeException("Account is deactivated");
        }

        return LoginMapper.toResponse(user);
    }
}