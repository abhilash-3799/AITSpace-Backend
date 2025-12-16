package com.ait.mrb_fp.exception;

public class EmployeeNotFoundException extends ResourceNotFoundException {
    public EmployeeNotFoundException(String employeeId) {
        super("Employee not found with ID: " + employeeId);
    }
}
