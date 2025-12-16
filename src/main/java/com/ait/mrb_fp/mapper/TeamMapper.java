package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.TeamRequestDTO;
import com.ait.mrb_fp.dto.response.TeamResponseDTO;
import com.ait.mrb_fp.entity.Team;

public class TeamMapper {

    private TeamMapper() {}

    public static Team toEntity(TeamRequestDTO r) {
        Team t = new Team();
        t.setTeamName(r.getTeamName());
        t.setDepartment(r.getDepartment());
        t.setActive(true);
        return t;
    }

    public static TeamResponseDTO toResponse(Team t) {
        TeamResponseDTO r = new TeamResponseDTO();
        r.setTeamId(t.getTeamId());
        r.setTeamName(t.getTeamName());
        r.setDepartment(t.getDepartment());
        r.setActive(t.isActive());
        return r;
    }

    public static void updateEntity(Team t, TeamRequestDTO r) {
        t.setTeamName(r.getTeamName());
        t.setDepartment(r.getDepartment());
    }
}
