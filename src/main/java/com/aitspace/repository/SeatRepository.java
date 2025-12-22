package com.aitspace.repository;

import com.aitspace.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {

    @Query("SELECT s FROM Seat s WHERE s.seatNumber = :seatNumber AND s.office.officeName = :officeName")
    Optional<Seat> findBySeatNumberAndOffice_OfficeName(
            @Param("seatNumber") String seatNumber,
            @Param("officeName") String officeName);

    List<Seat> findByOfficeOfficeName(String officeName);

    List<Seat> findByIsAvailableTrue();

    List<Seat> findBySeatStatus(Seat.SeatStatus status);
}