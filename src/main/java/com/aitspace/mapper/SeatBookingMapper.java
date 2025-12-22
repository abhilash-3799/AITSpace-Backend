package com.aitspace.mapper;

import com.aitspace.dto.response.SeatBookingResponseDTO;
import com.aitspace.entity.SeatBooking;

public class SeatBookingMapper {

    private SeatBookingMapper() {
    }

    public static SeatBookingResponseDTO toResponse(SeatBooking booking) {

        return SeatBookingResponseDTO.builder()
                .seatBookingId(booking.getSeatBookingId())
                .seatNumber(
                        booking.getSeat() != null
                                ? booking.getSeat().getSeatNumber()
                                : null
                )
                .employeeName(
                        booking.getEmployee() != null
                                ? booking.getEmployee().getFirstName() + " " +
                                booking.getEmployee().getLastName()
                                : null
                )
                .startDateTime(booking.getStartTime())
                .endDateTime(booking.getEndTime())
                .status(booking.getStatus().name())
                .isActive(booking.isActive())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .deletedAt(booking.getDeletedAt())
                .build();
    }
}
