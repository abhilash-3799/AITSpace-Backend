package com.aitspace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatCancellationRequestDTO {

    @NotBlank(message = "Employee ID is required")
    @Size(max = 50, message = "Employee ID must not exceed 50 characters")
    private String employeeId;

    @NotBlank(message = "Seat number is required")
    @Size(max = 20, message = "Seat number must not exceed 20 characters")
    private String seatNumber;

    @NotBlank(message = "Cancellation reason is required")
    @Size(max = 200, message = "Cancellation reason must not exceed 200 characters")
    private String cancellationReason;

    private String additionalNotes;
}