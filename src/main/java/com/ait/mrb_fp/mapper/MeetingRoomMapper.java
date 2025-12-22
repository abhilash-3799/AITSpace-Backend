package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.MeetingRoomRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomResponseDTO;
import com.ait.mrb_fp.entity.*;

public class MeetingRoomMapper {

    private MeetingRoomMapper() {}

    public static MeetingRoom toEntity(MeetingRoomRequestDTO r, Office office) {
        MeetingRoom m = new MeetingRoom();
        m.setOffice(office);
        m.setRoomName(r.getRoomName());
        m.setCapacity(r.getCapacity());
        m.setRoomType(MeetingRoom.RoomType.valueOf(String.valueOf(r.getRoomType())));
        m.setActive(true);
        return m;
    }

    public static MeetingRoomResponseDTO toResponse(MeetingRoom m) {
        MeetingRoomResponseDTO r = new MeetingRoomResponseDTO();
        r.setRoomId(m.getRoomId());
        r.setRoomName(m.getRoomName());
        r.setOfficeName(m.getOffice() != null ? m.getOffice().getOfficeName() : null);
        r.setCapacity(m.getCapacity());
        r.setRoomType(m.getRoomType().name());
        r.setActive(m.isActive());
        return r;
    }

    public static void updateEntity(MeetingRoom m, MeetingRoomRequestDTO r, Office office) {
        m.setOffice(office);
        m.setRoomName(r.getRoomName());
        m.setCapacity(r.getCapacity());
        m.setRoomType(MeetingRoom.RoomType.valueOf(String.valueOf(r.getRoomType())));
    }
}
