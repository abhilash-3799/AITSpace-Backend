package com.aitspace.service;

import com.aitspace.dto.request.EmployeeRequestDTO;
import com.aitspace.dto.response.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {


    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);

    EmployeeResponseDTO getEmployeeById(String employeeId);

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO updateEmployee(String employeeId, EmployeeRequestDTO dto);

    void deactivateEmployee(String employeeId);


}
