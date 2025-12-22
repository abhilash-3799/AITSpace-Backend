package com.aitspace.exception;

public class ShiftNotFoundException extends ResourceNotFoundException {
    public ShiftNotFoundException(String shiftId) {
        super("Shift not found with ID: " + shiftId);
    }
}
