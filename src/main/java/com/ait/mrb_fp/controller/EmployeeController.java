package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.EmployeeRequestDTO;
import com.ait.mrb_fp.dto.response.EmployeeResponseDTO;
import com.ait.mrb_fp.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Employee Controller", description = "APIs for managing employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Create a new employee", description = "Creates a new employee record")
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
        log.info("Request to create employee: {}", dto.getEmail());
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    @Operation(summary = "Get employee by ID", description = "Fetch employee details by employee ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable String id) {
        log.info("Fetching employee with ID: {}", id);
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(summary = "Get all employees", description = "Fetch all active employees")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        log.info("Fetching all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(summary = "Update employee details", description = "Update existing employee information")
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable("employeeId") String employeeId,
            @RequestBody EmployeeRequestDTO dto) {
        log.info("Updating employee with ID: {}", employeeId);
        EmployeeResponseDTO updated = employeeService.updateEmployee(employeeId, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deactivate employee", description = "Soft delete an employee by marking as inactive")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateEmployee(@PathVariable String id) {
        log.warn("Deactivating employee with ID: {}", id);
        employeeService.deactivateEmployee(id);
        return ResponseEntity.ok("Employee marked as inactive (soft deleted).");
    }
}
