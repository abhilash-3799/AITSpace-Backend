package com.ait.mrb_fp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponseDTO {
    private boolean success;
    private String message;
    private String loginId;
    private String username;
    private String email;
    private String employeeName;
    private String role;
}
