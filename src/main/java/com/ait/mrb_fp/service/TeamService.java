package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.TeamRequestDTO;
import com.ait.mrb_fp.dto.response.TeamResponseDTO;

import java.util.List;

public interface TeamService {
    TeamResponseDTO createTeam(TeamRequestDTO dto);

    TeamResponseDTO getTeamById(String teamId);

    List<TeamResponseDTO> getAllTeams();

    TeamResponseDTO updateTeam(String teamId, TeamRequestDTO dto);

    void deactivateTeam(String teamId);
}
