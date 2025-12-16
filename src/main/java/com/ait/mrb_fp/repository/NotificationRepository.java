package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> {
}
