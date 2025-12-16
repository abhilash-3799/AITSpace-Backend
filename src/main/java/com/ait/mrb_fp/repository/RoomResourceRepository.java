package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.RoomResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomResourceRepository extends JpaRepository<RoomResource, Long> {
    @Query("""
    SELECT r FROM RoomResource r
    LEFT JOIN FETCH r.amenities
    WHERE r.workspace.id = :workspaceId
      AND r.isActive = true
""")
    List<RoomResource> findByWorkspaceWithAmenities(@Param("workspaceId") Long workspaceId);

}

