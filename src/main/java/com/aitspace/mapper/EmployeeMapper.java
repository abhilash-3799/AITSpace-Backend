package com.aitspace.mapper;

import com.aitspace.dto.request.EmployeeRequestDTO;
import com.aitspace.dto.response.EmployeeResponseDTO;
import com.aitspace.entity.Employee;
import com.aitspace.entity.Office;
import com.aitspace.entity.Team;

public class EmployeeMapper {

    private EmployeeMapper() {}

    public static Employee toEntity(EmployeeRequestDTO request, Team team, Office office) {
        Employee e = new Employee();
        e.setEmployeeNumber(request.getEmployeeNumber());
        e.setFirstName(request.getFirstName());
        e.setLastName(request.getLastName());
        e.setEmail(request.getEmail());
        e.setTeam(team);
        e.setOffice(office);
        e.setEmployeeType(Employee.EmployeeType.valueOf(request.getEmployeeType()));
        e.setTeamLead(request.getTeamLead());
        e.setActive(true);
        return e;
    }

    public static EmployeeResponseDTO toResponse(Employee e) {
        return EmployeeResponseDTO.builder()
                .employeeId(e.getEmployeeId())
                .employeeNumber(e.getEmployeeNumber())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .email(e.getEmail())
                .teamName(e.getTeam() != null ? e.getTeam().getTeamName() : null)
                .officeName(e.getOffice() != null ? e.getOffice().getOfficeName() : null)
                .employeeType(e.getEmployeeType().name())
                .teamLead(e.isTeamLead())
                .isActive(e.isActive())
                .build();
    }

    public static void updateEntity(Employee existing, EmployeeRequestDTO request, Team team, Office office) {
        existing.setEmployeeNumber(request.getEmployeeNumber());

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());
        existing.setTeam(team);
        existing.setOffice(office);
        existing.setEmployeeType(Employee.EmployeeType.valueOf(request.getEmployeeType()));
        existing.setTeamLead(request.getTeamLead());

    }
}
