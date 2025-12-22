package com.ait.mrb_fp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {
    private String employeeId;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String teamName;
    private String officeName;
    private String employeeType;
    private boolean teamLead;
    private boolean isActive;
}
