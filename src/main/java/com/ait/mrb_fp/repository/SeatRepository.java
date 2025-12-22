package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, String> {

    @Query("""
           SELECT s FROM Seat s
           LEFT JOIN FETCH s.office
           LEFT JOIN FETCH s.assignedTeam
           LEFT JOIN FETCH s.queue
           """)
    List<Seat> findAllWithRelations();

    @Query("""
           SELECT s FROM Seat s
           LEFT JOIN FETCH s.office
           LEFT JOIN FETCH s.assignedTeam
           LEFT JOIN FETCH s.queue
           WHERE s.seatId = :id
           """)
    Seat findByIdWithRelations(String id);
}
