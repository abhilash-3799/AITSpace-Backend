package com.aitspace.repository;

import com.aitspace.entity.SeatBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, String> {

    @Query("SELECT COUNT(b) > 0 FROM SeatBooking b " +
            "WHERE b.seat.seatId = :seatId " +
            "AND b.isActive = true " +
            "AND b.status = 'ALLOCATED' " +
            "AND (:start BETWEEN b.startTime AND b.endTime " +
            "     OR :end BETWEEN b.startTime AND b.endTime " +
            "     OR b.startTime BETWEEN :start AND :end)")
    boolean existsOverlappingBooking(
            @Param("seatId") String seatId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    List<SeatBooking> findBySeatSeatIdAndIsActiveTrue(String seatId);

    List<SeatBooking> findByEmployeeEmployeeIdAndIsActiveTrue(String employeeId);

    @Query("SELECT COUNT(b) > 0 FROM SeatBooking b " +
            "WHERE b.seat.seatId = :seatId " +
            "AND b.isActive = true " +
            "AND b.status = 'ALLOCATED' " +
            "AND b.seatBookingId != :excludeBookingId " +
            "AND (:start BETWEEN b.startTime AND b.endTime " +
            "     OR :end BETWEEN b.startTime AND b.endTime " +
            "     OR b.startTime BETWEEN :start AND :end)")
    boolean existsOverlappingBookingExcludingCurrent(
            @Param("seatId") String seatId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("excludeBookingId") String excludeBookingId);
}