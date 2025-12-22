package com.aitspace.service;

import com.aitspace.dto.request.SignupRequestDTO;
import com.aitspace.dto.request.LoginRequestDTO;
import com.aitspace.dto.response.LoginResponseDTO;
import com.aitspace.dto.response.SignupResponseDTO;

public interface AuthService {

    SignupResponseDTO signup(SignupRequestDTO signupRequestDTO);

    LoginResponseDTO login(LoginRequestDTO LoginRequestDTO);
}
