package com.aitspace.mapper;

import com.aitspace.dto.request.OfficeRequestDTO;
import com.aitspace.dto.response.OfficeResponseDTO;
import com.aitspace.entity.Office;

public class OfficeMapper {

    private OfficeMapper() {}

    public static Office toEntity(OfficeRequestDTO r) {
        Office o = new Office();
        o.setOfficeName(r.getOfficeName());
        o.setLocation(r.getLocation());
        o.setTotalSeats(r.getTotalSeats());
        o.setActive(true);
        return o;
    }

    public static OfficeResponseDTO toResponse(Office o) {
        OfficeResponseDTO r = new OfficeResponseDTO();
        r.setOfficeId(o.getOfficeId());
        r.setOfficeName(o.getOfficeName());
        r.setLocation(o.getLocation());
        r.setTotalSeats(o.getTotalSeats());
        r.setActive(o.isActive());
        return r;
    }

    public static void updateEntity(Office o, OfficeRequestDTO r) {
        o.setOfficeName(r.getOfficeName());
        o.setLocation(r.getLocation());
        o.setTotalSeats(r.getTotalSeats());
    }
}
