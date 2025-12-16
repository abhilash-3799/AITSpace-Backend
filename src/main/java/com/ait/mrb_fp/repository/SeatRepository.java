package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, String> {
}

