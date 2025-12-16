package com.ait.mrb_fp.exception;

public class MeetingRoomNotFoundException extends ResourceNotFoundException {
    public MeetingRoomNotFoundException(String roomId) {
        super("Meeting room not found with ID: " + roomId);
    }
}
