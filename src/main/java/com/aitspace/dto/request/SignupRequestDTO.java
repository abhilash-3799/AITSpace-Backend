// SignupRequestDTO.java
package com.aitspace.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDTO {

    @NotBlank
    private String employeeId;

    @NotBlank
    private String username;

    @NotBlank
    @Email(message = "Please provide a valid email address")
    private String email;  

    @NotBlank
    private String password;

    @NotBlank
    private String role;
}