package com.aitspace.repository;

import com.aitspace.entity.SeatBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatBookingRepository extends JpaRepository<SeatBooking, String> {

    List<SeatBooking> findByIsActiveTrue();

    @Query("""
        SELECT sb FROM SeatBooking sb
        WHERE sb.isActive = true
        AND sb.endDateTime < :now
    """)
    List<SeatBooking> findExpiredBookings(LocalDateTime now);

    @Query("""
        SELECT COUNT(sb) > 0 FROM SeatBooking sb
        WHERE sb.seat.seatId = :seatId
        AND sb.isActive = true
        AND (:start < sb.endDateTime AND :end > sb.startDateTime)
    """)
    boolean existsOverlappingBooking(
            String seatId,
            LocalDateTime start,
            LocalDateTime end
    );
}
