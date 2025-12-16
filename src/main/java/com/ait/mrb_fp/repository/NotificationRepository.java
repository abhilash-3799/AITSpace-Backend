package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Notification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    @Query("SELECT n FROM Notification n JOIN FETCH n.employee")
    List<Notification> findAllWithEmployee();

    @Query("SELECT n FROM Notification n JOIN FETCH n.employee WHERE n.notificationId = :id")
    Notification findByIdWithEmployee(@Param("id") String id);


    @Query("SELECT n FROM Notification n JOIN FETCH n.employee WHERE n.notificationType = 'SEAT_CANCELLATION'")
    List<Notification> findSeatCancellationNotifications();

    @Query("SELECT n FROM Notification n JOIN FETCH n.employee WHERE n.employee.employeeId = :employeeId AND n.notificationType = :type")
    List<Notification> findByEmployeeIdAndType(@Param("employeeId") String employeeId,
                                               @Param("type") Notification.NotificationType type);

    @Query("SELECT n FROM Notification n WHERE n.seatNumber = :seatNumber AND n.isActive = true")
    List<Notification> findBySeatNumberAndActiveTrue(@Param("seatNumber") String seatNumber);

    @Query("SELECT n FROM Notification n JOIN FETCH n.employee WHERE n.seatNumber = :seatNumber AND n.notificationType = 'SEAT_CANCELLATION'")
    List<Notification> findSeatCancellationBySeatNumber(@Param("seatNumber") String seatNumber);


    @Query("SELECT n FROM Notification n JOIN FETCH n.employee WHERE n.notificationType = :notificationType")
    List<Notification> findByNotificationType(@Param("notificationType") Notification.NotificationType notificationType);


}
