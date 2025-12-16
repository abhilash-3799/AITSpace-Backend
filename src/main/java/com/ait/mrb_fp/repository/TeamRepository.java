package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.dto.response.TeamResponseDTO;
import com.ait.mrb_fp.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String>
{

    @Query("""
           SELECT new com.ait.mrb_fp.dto.response.TeamResponseDTO
           (t.teamId, t.teamName, t.department, t.isActive)
           FROM Team t
           WHERE t.isActive = true
           """)
    List<TeamResponseDTO> findAllActiveAsDto();

    @Query("""
           SELECT new com.ait.mrb_fp.dto.response.TeamResponseDTO
           (t.teamId, t.teamName, t.department, t.isActive)
           FROM Team t
           WHERE t.teamId = :id
           """)
    Optional<TeamResponseDTO> findByIdAsDto(String id);

    List<Team> findByIsActiveTrue();

    boolean existsByTeamName(String teamName);

    Team findByTeamName(String teamName);

    boolean existsByTeamNameIgnoreCase(String normalizedName);



}

