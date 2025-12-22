package com.aitspace.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDTO {

    @NotBlank(message = "Employee number is required.")
    @Size(max = 20, message = "Employee number must be at most 20 characters.")
    private String employeeNumber;

    @NotBlank(message = "First name is required.")
    @Size(max = 50, message = "First name must be at most 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Last name must be at most 50 characters.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    @Size(max = 100, message = "Email must be at most 100 characters.")
    private String email;

    @NotBlank(message = "Team ID is required.")
    private String teamId;

    @NotBlank(message = "Office ID is required.")
    private String officeId;

    @NotBlank(message = "Employee type is required.")
    private String employeeType;

    @NotNull(message = "Team lead flag is required.")
    private Boolean teamLead;
}
