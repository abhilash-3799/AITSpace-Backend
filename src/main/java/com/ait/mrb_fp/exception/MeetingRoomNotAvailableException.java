package com.ait.mrb_fp.exception;

public class MeetingRoomNotAvailableException extends BadRequestException {
    public MeetingRoomNotAvailableException(String message) {
        super("Meeting room not available: " + message);
    }
}
