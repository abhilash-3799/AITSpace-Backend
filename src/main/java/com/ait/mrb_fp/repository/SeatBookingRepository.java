package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.SeatBooking;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, String> {

    boolean existsBySeat_SeatIdAndSeatBookingDate(String seatId, LocalDateTime seatBookingDate);

    boolean existsByEmployee_EmployeeId(String employeeId);

    boolean existsByEmployee_EmployeeIdAndSeat_SeatId(String employeeId, String seatId);

    boolean existsByEmployee_EmployeeIdAndSeat_SeatIdAndSeatBookingDate(String employeeId, String seatId, LocalDateTime seatBookingDate);

    boolean existsBySeat_SeatIdAndSeatBookingDateAndEmployee_EmployeeIdNot(
            String seatId,
            LocalDateTime seatBookingDate,
            String employeeId
    );

    @Query("SELECT CASE WHEN COUNT(sb) > 0 THEN TRUE ELSE FALSE END " +
            "FROM SeatBooking sb WHERE sb.seat.seatId = :seatId " +
            "AND sb.seatBookingDate BETWEEN :startOfDay AND :endOfDay")
    boolean existsBySeatAndDateRange(@Param("seatId") String seatId,
                                     @Param("startOfDay") LocalDateTime startOfDay,
                                     @Param("endOfDay") LocalDateTime endOfDay);


    boolean existsByEmployee_EmployeeIdAndSeatBookingDateBetween(
            String employeeId,
            LocalDateTime start,
            LocalDateTime end
    );

    SeatBooking findFirstBySeat_SeatIdAndSeatBookingDateBetween(
            String seatId,
            LocalDateTime start,
            LocalDateTime end
    );






}
