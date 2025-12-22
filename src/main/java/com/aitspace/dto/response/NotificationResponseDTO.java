package com.aitspace.dto.response;

import com.aitspace.entity.Notification.NotificationType;
import lombok.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;


    private String seatNumber;
    private String cancellationReason;
    private LocalDateTime cancellationDate;
    private NotificationType notificationType;
}