//package com.ait.mrb_fp.mapper;
//
//import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
//import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
//import com.ait.mrb_fp.entity.*;
//
//public class NotificationMapper {
//
//    private NotificationMapper() {}
//
//    public static Notification toEntity(NotificationRequestDTO r, Employee employee) {
//        Notification n = new Notification();
//        n.setEmployee(employee);
//        n.setTitle(r.getTitle());
//        n.setMessage(r.getMessage());
//        n.setActive(true);
//        return n;
//    }
//
//    public static NotificationResponseDTO toResponse(Notification n) {
//        NotificationResponseDTO r = new NotificationResponseDTO();
//        r.setNotificationId(n.getNotificationId());
//        r.setEmployeeName(n.getEmployee() != null ? n.getEmployee().getFirstName() + " " + n.getEmployee().getLastName() : null);
//        r.setTitle(n.getTitle());
//        r.setMessage(n.getMessage());
//        r.setActive(n.isActive());
//        return r;
//    }
//
//    public static void updateEntity(Notification n, NotificationRequestDTO r, Employee employee) {
//        n.setEmployee(employee);
//        n.setTitle(r.getTitle());
//        n.setMessage(r.getMessage());
//    }
//}
package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.entity.*;

import java.time.LocalDateTime;

public class NotificationMapper {

    private NotificationMapper() {}

    public static Notification toEntity(NotificationRequestDTO r, Employee employee) {
        Notification n = new Notification();
        n.setEmployee(employee);
        n.setTitle(r.getTitle());
        n.setMessage(r.getMessage());
        n.setActive(true);
        n.setSeatNumber(r.getSeatNumber());
        n.setCancellationReason(r.getCancellationReason());
        n.setNotificationType(r.getNotificationType());

        // Set cancellation date if it's a cancellation notification
        if (r.getNotificationType() == Notification.NotificationType.SEAT_CANCELLATION) {
            n.setCancellationDate(LocalDateTime.now());
        }

        return n;
    }

    public static NotificationResponseDTO toResponse(Notification n) {
        NotificationResponseDTO r = new NotificationResponseDTO();
        r.setNotificationId(n.getNotificationId());
        r.setEmployeeName(n.getEmployee() != null ? n.getEmployee().getFirstName() + " " + n.getEmployee().getLastName() : null);
        r.setTitle(n.getTitle());
        r.setMessage(n.getMessage());
        r.setActive(n.isActive());
        r.setCreatedAt(n.getCreatedAt());
        r.setSeatNumber(n.getSeatNumber());
        r.setCancellationReason(n.getCancellationReason());
        r.setCancellationDate(n.getCancellationDate());
        r.setNotificationType(n.getNotificationType());
        return r;
    }

    public static void updateEntity(Notification n, NotificationRequestDTO r, Employee employee) {
        n.setEmployee(employee);
        n.setTitle(r.getTitle());
        n.setMessage(r.getMessage());
        n.setSeatNumber(r.getSeatNumber());
        n.setCancellationReason(r.getCancellationReason());
        n.setNotificationType(r.getNotificationType());

        // Update cancellation date if changing to cancellation type
        if (r.getNotificationType() == Notification.NotificationType.SEAT_CANCELLATION && n.getCancellationDate() == null) {
            n.setCancellationDate(LocalDateTime.now());
        }
    }
}