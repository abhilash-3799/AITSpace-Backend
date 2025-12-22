package com.ait.mrb_fp.exception;

public class BookingConflictException extends DuplicateResourceException {
    public BookingConflictException(String message) {
        super("Booking conflict detected: " + message);
    }
}
