package com.ait.mrb_fp.exception;

public class TeamNotFoundException extends ResourceNotFoundException {
    public TeamNotFoundException(String teamId) {
        super("Team not found with ID: " + teamId);
    }
}
