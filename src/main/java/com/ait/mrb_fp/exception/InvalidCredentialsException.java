package com.ait.mrb_fp.exception;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException() {
        super("Invalid username or password.");
    }
}
