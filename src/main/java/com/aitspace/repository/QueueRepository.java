package com.aitspace.repository;

import com.aitspace.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QueueRepository extends JpaRepository<Queue, String> {

    @Query("""
           SELECT q FROM Queue q
           LEFT JOIN FETCH q.office
           """)
    List<Queue> findAllWithRelations();

    @Query("""
           SELECT q FROM Queue q
           LEFT JOIN FETCH q.office
           WHERE q.queueId = :id
           """)
    Queue findByIdWithRelations(String id);
}
