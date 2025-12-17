package com.aitspace.repository;

import com.aitspace.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, String> {

    Optional<Seat> findBySeatNumberAndOffice_OfficeName(
            String seatNumber,
            String officeName
    );
}
