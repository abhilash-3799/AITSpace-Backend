package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.MeetingRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomBookingRepository extends JpaRepository<MeetingRoomBooking, String> {
    boolean existsByBookedBy_EmployeeIdAndRoom_RoomId(String employeeId, String roomId);

    List<MeetingRoomBooking> findByRoom_RoomIdAndMeetingDate(String roomId, LocalDate meetingDate);



    boolean existsByRoom_RoomIdAndMeetingDateAndStartTimeLessThanAndEndTimeGreaterThan(
            String roomId,
            LocalDate meetingDate,
            LocalDateTime endTime,
            LocalDateTime startTime
    );



}
