package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.EmployeeRequestDTO;
import com.ait.mrb_fp.dto.response.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {


    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);

    EmployeeResponseDTO getEmployeeById(String employeeId);

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO updateEmployee(String employeeId, EmployeeRequestDTO dto);

    void deactivateEmployee(String employeeId);


}
