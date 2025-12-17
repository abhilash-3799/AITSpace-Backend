package com.aitspace.exception;

public class OfficeNotFoundException extends ResourceNotFoundException {
    public OfficeNotFoundException(String officeId) {
        super("Office not found with ID: " + officeId);
    }
}
