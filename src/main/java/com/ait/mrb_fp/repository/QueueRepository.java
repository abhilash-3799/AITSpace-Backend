package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueRepository extends JpaRepository<Queue, String> {
}
