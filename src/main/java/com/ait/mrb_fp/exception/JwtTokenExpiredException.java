package com.ait.mrb_fp.exception;

public class JwtTokenExpiredException extends UnauthorizedException {
    public JwtTokenExpiredException() {
        super("JWT token has expired or is invalid.");
    }
}
