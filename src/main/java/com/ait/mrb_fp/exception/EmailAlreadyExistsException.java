package com.ait.mrb_fp.exception;

public class EmailAlreadyExistsException extends DuplicateResourceException {
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}
