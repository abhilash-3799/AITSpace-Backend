package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.SeatBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

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
}
