package com.aitspace.controller;

import com.aitspace.dto.request.TeamRequestDTO;
import com.aitspace.dto.response.TeamResponseDTO;
import com.aitspace.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Team Controller", description = "APIs for managing teams")
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "Create a new team", description = "Adds a new team")
    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@Valid @RequestBody TeamRequestDTO dto) {
        log.info("Creating new team: {}", dto.getTeamName());
        return ResponseEntity.ok(teamService.createTeam(dto));
    }

    @Operation(summary = "Get team by ID", description = "Fetch team details using ID")
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable String id) {
        log.info("Fetching team with ID: {}", id);
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @Operation(summary = "Get all teams", description = "Retrieve all active teams")
    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        log.info("Fetching all active teams");
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @Operation(summary = "Update a team", description = "Update team details by ID")
    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> updateTeam(@PathVariable String id, @RequestBody TeamRequestDTO dto) {
        log.info("Updating team with ID: {}", id);
        return ResponseEntity.ok(teamService.updateTeam(id, dto));
    }

    @Operation(summary = "Deactivate a team", description = "Soft delete (set isActive = false)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateTeam(@PathVariable String id) {
        log.warn("Deactivating team with ID: {}", id);
        teamService.deactivateTeam(id);
        return ResponseEntity.ok("Team marked as inactive (soft deleted).");
    }
}
