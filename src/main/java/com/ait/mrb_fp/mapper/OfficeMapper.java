package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.OfficeRequestDTO;
import com.ait.mrb_fp.dto.response.OfficeResponseDTO;
import com.ait.mrb_fp.entity.Office;

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
