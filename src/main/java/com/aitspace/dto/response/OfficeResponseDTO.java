package com.aitspace.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeResponseDTO {
    private String officeId;
    private String officeName;
    private String location;
    private int totalSeats;
    private boolean isActive;
}
