package com.aitspace.repository;

import com.aitspace.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByRoomNameAndDate(String roomName, LocalDate date);




    List<Booking> findByRoomNameAndDateAndStatus(String roomName, LocalDate date, String status);

    @Query("SELECT b FROM Booking b WHERE b.roomName = :roomName " +
            "AND b.date = :date " +
            "AND b.status = 'Active' " +
            "AND ((b.startTime < :endTime AND b.endTime > :startTime))")
    List<Booking> findOverlappingBookings(
            @Param("roomName") String roomName,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);

    @Query("SELECT b FROM Booking b WHERE b.roomName = :roomName " +
            "AND b.date = :date " +
            "AND b.status = 'Active'")
    List<Booking> findActiveBookingsForRoomOnDate(
            @Param("roomName") String roomName,
            @Param("date") LocalDate date);

    List<Booking> findByOfficeNameAndStatus(String officeName, String status);
}
