package com.ait.mrb_fp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {
    private String notificationId;
    private String employeeName;
    private String title;
    private String message;
    private boolean isActive;
}
