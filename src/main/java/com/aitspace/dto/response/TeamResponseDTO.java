package com.aitspace.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponseDTO {
    private String teamId;
    private String teamName;
    private String department;
    private boolean isActive;




}
