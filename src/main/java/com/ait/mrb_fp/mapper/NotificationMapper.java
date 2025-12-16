package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.NotificationRequestDTO;
import com.ait.mrb_fp.dto.response.NotificationResponseDTO;
import com.ait.mrb_fp.entity.*;

public class NotificationMapper {

    private NotificationMapper() {}

    public static Notification toEntity(NotificationRequestDTO r, Employee employee) {
        Notification n = new Notification();
        n.setEmployee(employee);
        n.setTitle(r.getTitle());
        n.setMessage(r.getMessage());
        n.setActive(true);
        return n;
    }

    public static NotificationResponseDTO toResponse(Notification n) {
        NotificationResponseDTO r = new NotificationResponseDTO();
        r.setNotificationId(n.getNotificationId());
        r.setEmployeeName(n.getEmployee() != null ? n.getEmployee().getFirstName() + " " + n.getEmployee().getLastName() : null);
        r.setTitle(n.getTitle());
        r.setMessage(n.getMessage());
        r.setActive(n.isActive());
        return r;
    }

    public static void updateEntity(Notification n, NotificationRequestDTO r, Employee employee) {
        n.setEmployee(employee);
        n.setTitle(r.getTitle());
        n.setMessage(r.getMessage());
    }
}
