package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrb_fp.entity.*;

public class MeetingRoomBookingMapper {

    private MeetingRoomBookingMapper() {}

    public static MeetingRoomBooking toEntity(MeetingRoomBookingRequestDTO r, MeetingRoom room, Employee bookedBy) {
        MeetingRoomBooking m = new MeetingRoomBooking();
        m.setRoom(room);
        m.setBookedBy(bookedBy);
        m.setMeetingDate(r.getMeetingDate());
        m.setStartTime(r.getStartTime());
        m.setEndTime(r.getEndTime());
        m.setPurpose(r.getPurpose());
        m.setStatus(MeetingRoomBooking.MeetingStatus.valueOf(String.valueOf(r.getStatus())));
        m.setActive(true);
        return m;
    }

    public static MeetingRoomBookingResponseDTO toResponse(MeetingRoomBooking m) {
        MeetingRoomBookingResponseDTO r = new MeetingRoomBookingResponseDTO();
        r.setMeetingId(m.getMeetingId());
        r.setRoomName(m.getRoom() != null ? m.getRoom().getRoomName() : null);
        r.setBookedBy(m.getBookedBy() != null ? m.getBookedBy().getFirstName() + " " + m.getBookedBy().getLastName() : null);
        r.setMeetingDate(m.getMeetingDate());
        r.setStartTime(m.getStartTime());
        r.setEndTime(m.getEndTime());
        r.setPurpose(m.getPurpose());
        r.setStatus(m.getStatus().name());
        r.setActive(m.isActive());
        return r;
    }

    public static void updateEntity(MeetingRoomBooking m, MeetingRoomBookingRequestDTO r, MeetingRoom room, Employee bookedBy) {
        m.setRoom(room);
        m.setBookedBy(bookedBy);
        m.setMeetingDate(r.getMeetingDate());
        m.setStartTime(r.getStartTime());
        m.setEndTime(r.getEndTime());
        m.setPurpose(r.getPurpose());
        m.setStatus(MeetingRoomBooking.MeetingStatus.valueOf(String.valueOf(r.getStatus())));
    }
}
