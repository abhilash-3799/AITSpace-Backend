package com.aitspace.service;

import com.aitspace.dto.request.TeamRequestDTO;
import com.aitspace.dto.response.TeamResponseDTO;

import java.util.List;

public interface TeamService {
    TeamResponseDTO createTeam(TeamRequestDTO dto);

    TeamResponseDTO getTeamById(String teamId);

    List<TeamResponseDTO> getAllTeams();

    TeamResponseDTO updateTeam(String teamId, TeamRequestDTO dto);

    void deactivateTeam(String teamId);
}
