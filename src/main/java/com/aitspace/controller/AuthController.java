package com.aitspace.controller;

import com.aitspace.dto.request.LoginRequestDTO;
import com.aitspace.dto.request.SignupRequestDTO;
import com.aitspace.dto.response.LoginResponseDTO;
import com.aitspace.dto.response.SignupResponseDTO;
import com.aitspace.security.JwtUtil;
import com.aitspace.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    //private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody SignupRequestDTO req) {
        SignupResponseDTO response = authService.signup(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO req) {
        LoginResponseDTO user = authService.login(req);
        return ResponseEntity.ok(user);
    }
}

