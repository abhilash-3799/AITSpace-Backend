//package com.ait.mrb_fp.mapper;
//
//import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
//import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
//import com.ait.mrb_fp.entity.Employee;
//import com.ait.mrb_fp.entity.Seat;
//import com.ait.mrb_fp.entity.SeatBooking;
//
//public class SeatBookingMapper {
//
//    private SeatBookingMapper() {}
//
//    // ✅ Convert RequestDTO to Entity
//    public static SeatBooking toEntity(SeatBookingRequestDTO dto, Seat seat, Employee employee) {
//        SeatBooking booking = new SeatBooking();
//        booking.setSeat(seat);
//        booking.setEmployee(employee);
//      booking.setAllocationDate(dto.getAllocationDate());
//
//
//        booking.setStatus(SeatBooking.BookingStatus.valueOf(dto.getStatus()));
//        booking.setActive(true);
//        return booking;
//    }
//
//    // ✅ Convert Entity to ResponseDTO
//    public static SeatBookingResponseDTO toResponse(SeatBooking booking) {
//        if (booking == null) return null;
//
//        return SeatBookingResponseDTO.builder()
//                .allocationId(booking.getAllocationId())
//                .seatNumber(booking.getSeat() != null ? booking.getSeat().getSeatNumber() : null)
//                .employeeName(booking.getEmployee() != null
//                        ? booking.getEmployee().getFirstName() + " " + booking.getEmployee().getLastName()
//                        : null)
//                .allocationDate(booking.getAllocationDate())
//                .status(booking.getStatus() != null ? booking.getStatus().name() : null)
//                .isActive(booking.isActive())
//                .build();
//    }
//
//    // ✅ Update existing booking from RequestDTO
//    public static void updateEntity(SeatBooking existing, SeatBookingRequestDTO dto, Seat seat, Employee employee) {
//        existing.setSeat(seat);
//        existing.setEmployee(employee);
//        existing.setAllocationDate(dto.getAllocationDate());
//        existing.setStatus(SeatBooking.BookingStatus.valueOf(dto.getStatus()));
//    }
//}

package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Seat;
import com.ait.mrb_fp.entity.SeatBooking;

import java.time.LocalDateTime;

public class SeatBookingMapper {

    private SeatBookingMapper() {}


    public static SeatBooking toEntity(SeatBookingRequestDTO dto, Seat seat, Employee employee) {
        SeatBooking booking = new SeatBooking();
        booking.setSeat(seat);
        booking.setEmployee(employee);


        booking.setSeatBookingDate(LocalDateTime.now());


        booking.setStatus(SeatBooking.BookingStatus.valueOf(String.valueOf(dto.getStatus())));
        booking.setActive(true);




        return booking;
    }


    public static SeatBookingResponseDTO toResponse(SeatBooking booking) {
        if (booking == null) return null;

        return SeatBookingResponseDTO.builder()
                .allocationId(booking.getSeatBookingId())
                .seatNumber(booking.getSeat() != null ? booking.getSeat().getSeatNumber() : null)
                .employeeName(booking.getEmployee() != null
                        ? booking.getEmployee().getFirstName() + " " + booking.getEmployee().getLastName()
                        : null)
                .allocationDate(booking.getSeatBookingDate())
                .status(booking.getStatus() != null ? booking.getStatus().name() : null)
                .isActive(booking.isActive())
                .build();
    }


    public static void updateEntity(SeatBooking existing, SeatBookingRequestDTO dto, Seat seat, Employee employee) {
        existing.setSeat(seat);
        existing.setEmployee(employee);
        existing.setStatus(SeatBooking.BookingStatus.valueOf(String.valueOf(dto.getStatus())));


        if (dto.getSeatBookingDate() != null) {
            existing.setSeatBookingDate(dto.getSeatBookingDate());
        }
    }
}
