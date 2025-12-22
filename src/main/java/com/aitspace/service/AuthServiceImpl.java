package com.aitspace.service;

import com.aitspace.dto.request.SignupRequestDTO;
import com.aitspace.dto.request.LoginRequestDTO;
import com.aitspace.dto.response.SignupResponseDTO;
import com.aitspace.dto.response.LoginResponseDTO;
import com.aitspace.entity.UserLogin;
import com.aitspace.exception.*;
import com.aitspace.exception.AccountDeactivatedException;
import com.aitspace.exception.InvalidCredentialsException;
import com.aitspace.exception.ResourceNotFoundException;
import com.aitspace.exception.UserAlreadyExistsException;
import com.aitspace.mapper.LoginMapper;
import com.aitspace.mapper.SignupMapper;
import com.aitspace.repository.EmployeeRepository;
import com.aitspace.repository.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserLoginRepository userLoginRepo;
    private final EmployeeRepository employeeRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SignupResponseDTO signup(SignupRequestDTO req) {
        log.info("Processing signup request for username: {}", req.getUsername());

        // Normalize email
        String normalizedEmail = req.getEmail().toLowerCase().trim();
        req.setEmail(normalizedEmail);

        // Check if username exists
        if (userLoginRepo.existsByUsername(req.getUsername())) {
            log.warn("Signup failed - Username already exists: {}", req.getUsername());
            throw new UserAlreadyExistsException("Username already exists");
        }

        // Check if email exists
        if (userLoginRepo.existsByEmail(normalizedEmail)) {
            log.warn("Signup failed - Email already registered: {}", normalizedEmail);
            throw new UserAlreadyExistsException("Email already registered");
        }

        // Find employee
        var employee = employeeRepo.findById(req.getEmployeeId())
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", req.getEmployeeId());
                    return new ResourceNotFoundException("Employee not found");
                });

        // Create user login
        UserLogin user = SignupMapper.toEntity(req, employee, passwordEncoder);
        userLoginRepo.save(user);

        log.info("Signup successful for username: {}, loginId: {}", req.getUsername(), user.getLoginId());

        return SignupMapper.toResponse(user, "Signup successful", true);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        log.info("Processing login request for email: {}", loginRequestDTO.getEmail());

        // Normalize email
        String normalizedEmail = loginRequestDTO.getEmail().toLowerCase().trim();

        // Find user by email
        var user = userLoginRepo.findByEmail(normalizedEmail)
                .orElseThrow(() -> {
                    log.warn("Login failed - Invalid credentials for email: {}", normalizedEmail);
                    return new InvalidCredentialsException("Invalid email or password");
                });

        // Verify password
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            log.warn("Login failed - Invalid password for email: {}", normalizedEmail);
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Check if account is active
        if (!user.isActive()) {
            log.warn("Login failed - Account deactivated for email: {}", normalizedEmail);
            throw new AccountDeactivatedException("Account is deactivated. Please contact administrator.");
        }

        log.info("Login successful for email: {}", normalizedEmail);
        return LoginMapper.toResponse(user);
    }
}