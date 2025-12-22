package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.SeatRequestDTO;
import com.ait.mrb_fp.dto.response.SeatResponseDTO;
import com.ait.mrb_fp.entity.*;

public class SeatMapper {

    private SeatMapper() {}

    public static Seat toEntity(SeatRequestDTO r, Office office, Team team, Queue queue) {
        Seat s = new Seat();
        s.setOffice(office);
        s.setSeatNumber(r.getSeatNumber());
        s.setAssignedTeam(team);
        s.setQueue(queue);
        s.setSeatStatus(Seat.SeatStatus.valueOf(String.valueOf(r.getSeatStatus())));
        s.setAvailable(r.isAvailable());
        s.setActive(true);
        return s;
    }

    public static SeatResponseDTO toResponse(Seat s) {
        SeatResponseDTO r = new SeatResponseDTO();
        r.setSeatId(s.getSeatId());
        r.setSeatNumber(s.getSeatNumber());
        r.setOfficeName(s.getOffice() != null ? s.getOffice().getOfficeName() : null);
        r.setTeamName(s.getAssignedTeam() != null ? s.getAssignedTeam().getTeamName() : null);
        r.setQueueName(s.getQueue() != null ? s.getQueue().getQueueName() : null);
        r.setSeatStatus(s.getSeatStatus().name());
        r.setAvailable(s.isAvailable());
        r.setActive(s.isActive());
        return r;
    }

    public static void updateEntity(Seat s, SeatRequestDTO r, Office office, Team team, Queue queue) {
        s.setOffice(office);
        s.setSeatNumber(r.getSeatNumber());
        s.setAssignedTeam(team);
        s.setQueue(queue);
        s.setSeatStatus(Seat.SeatStatus.valueOf(String.valueOf(r.getSeatStatus())));
        s.setAvailable(r.isAvailable());
    }
}
