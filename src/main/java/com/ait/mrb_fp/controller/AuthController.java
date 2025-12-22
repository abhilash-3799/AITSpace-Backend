package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.LoginRequestDTO;
import com.ait.mrb_fp.dto.request.SignupRequestDTO;
import com.ait.mrb_fp.dto.response.LoginResponseDTO;
import com.ait.mrb_fp.dto.response.SignupResponseDTO;
//import com.aitspace.security.JwtUtil;
import com.ait.mrb_fp.service.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO req) {
        LoginResponseDTO user = authService.login(req);
        return ResponseEntity.ok(user);
    }
}
