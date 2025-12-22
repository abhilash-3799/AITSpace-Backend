package com.aitspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String loginId;
    private String employeeId;
    private String employeeNumber;
    private String employeeName;
    private String username;
    private String email;
    private String role;
    private boolean isActive;
}
