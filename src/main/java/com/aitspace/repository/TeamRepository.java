package com.aitspace.repository;

import com.aitspace.dto.response.TeamResponseDTO;
import com.aitspace.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String>
{

    @Query("""
           SELECT new com.aitspace.dto.response.TeamResponseDTO
           (t.teamId, t.teamName, t.department, t.isActive)
           FROM Team t
           WHERE t.isActive = true
           """)
    List<TeamResponseDTO> findAllActiveAsDto();

    @Query("""
           SELECT new com.aitspace.dto.response.TeamResponseDTO
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

