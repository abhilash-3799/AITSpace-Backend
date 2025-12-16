package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.SignupRequestDTO;
import com.ait.mrb_fp.dto.request.LoginRequestDTO;
import com.ait.mrb_fp.dto.response.LoginResponseDTO;
import com.ait.mrb_fp.dto.response.SignupResponseDTO;

public interface AuthService {

    SignupResponseDTO signup(SignupRequestDTO signupRequestDTO);

    LoginResponseDTO login(LoginRequestDTO LoginRequestDTO);
}
