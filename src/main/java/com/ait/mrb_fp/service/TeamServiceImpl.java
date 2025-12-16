package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.TeamRequestDTO;
import com.ait.mrb_fp.dto.response.TeamResponseDTO;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.exception.*;
import com.ait.mrb_fp.mapper.TeamMapper;
import com.ait.mrb_fp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;


    @Override
    public TeamResponseDTO createTeam(TeamRequestDTO dto) {
        try {
            if (dto == null) {
                throw new InvalidRequestBodyException("Team request body cannot be null.");
            }

            if (dto.getTeamName() == null || dto.getTeamName().isBlank()) {
                throw new MissingRequestParameterException("Team name is required.");
            }


            boolean exists = teamRepository.existsByTeamName(dto.getTeamName());
            if (exists) {
                throw new DuplicateResourceException("Team with name already exists: " + dto.getTeamName());
            }

            Team team = TeamMapper.toEntity(dto);

            team.setActive(true);

            return TeamMapper.toResponse(teamRepository.save(team));

        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred while creating team.");
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed while creating team.");
        }
    }


    @Override
    public TeamResponseDTO getTeamById(String teamId) {
        if (teamId == null || teamId.isBlank()) {
            throw new MissingRequestParameterException("Team ID cannot be empty.");
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team not found with ID: " + teamId));

        if (!team.isActive()) {
            throw new InvalidStateException("Team is inactive. Reactivate before accessing.");
        }

        return TeamMapper.toResponse(team);
    }


    @Override
    public List<TeamResponseDTO> getAllTeams() {
        try {
            return teamRepository.findByIsActiveTrue()
                    .stream()
                    .map(TeamMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred while fetching team list.");
        }
    }


    @Override
    public TeamResponseDTO updateTeam(String teamId, TeamRequestDTO dto) {
        try {
            if (teamId == null || teamId.isBlank()) {
                throw new MissingRequestParameterException("Team ID is required for update.");
            }

            if (dto == null) {
                throw new InvalidRequestBodyException("Team update request cannot be null.");
            }

            Team existing = teamRepository.findById(teamId)
                    .orElseThrow(() -> new TeamNotFoundException("Team not found with ID: " + teamId));

            if (!existing.isActive()) {
                throw new InvalidStateException("Cannot update an inactive team.");
            }


            Team duplicate = teamRepository.findByTeamName(dto.getTeamName());
            if (duplicate != null && !duplicate.getTeamId().equals(teamId)) {
                throw new DuplicateResourceException("Another team already exists with name: " + dto.getTeamName());
            }

            existing.setTeamName(dto.getTeamName());
            existing.setDepartment(dto.getDepartment());

            return TeamMapper.toResponse(teamRepository.save(existing));

        } catch (DataAccessException ex) {
            throw new DatabaseException("Error occurred while updating team.");
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed during team update.");
        }
    }


    @Override
    public void deactivateTeam(String teamId) {
        try {
            if (teamId == null || teamId.isBlank()) {
                throw new MissingRequestParameterException("Team ID is required for deactivation.");
            }

            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new TeamNotFoundException("Team not found with ID: " + teamId));

            if (!team.isActive()) {
                throw new InvalidStateException("Team is already inactive.");
            }

            team.setActive(false);
            teamRepository.save(team);

        } catch (DataAccessException ex) {
            throw new DatabaseException("Error updating team status in database.");
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed while deactivating team.");
        }
    }
}
