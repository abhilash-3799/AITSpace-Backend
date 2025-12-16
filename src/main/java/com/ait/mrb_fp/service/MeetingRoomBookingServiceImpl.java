package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.MeetingRoom;
import com.ait.mrb_fp.entity.MeetingRoomBooking;
import com.ait.mrb_fp.mapper.MeetingRoomBookingMapper;
import com.ait.mrb_fp.repository.EmployeeRepository;
import com.ait.mrb_fp.repository.MeetingRoomBookingRepository;
import com.ait.mrb_fp.repository.MeetingRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRoomBookingServiceImpl implements MeetingRoomBookingService {

    private final MeetingRoomBookingRepository bookingRepository;
    private final MeetingRoomRepository roomRepository;
    private final EmployeeRepository employeeRepository;

    public MeetingRoomBookingServiceImpl(
            MeetingRoomBookingRepository bookingRepository,
            MeetingRoomRepository roomRepository,
            EmployeeRepository employeeRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<MeetingRoomBookingResponseDTO> getAll() {
        return bookingRepository.findAll()
                .stream()
                .map(MeetingRoomBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingRoomBookingResponseDTO getById(String id) {
        MeetingRoomBooking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meeting booking not found: " + id));
        return MeetingRoomBookingMapper.toResponse(booking);
    }


    @Override
    public MeetingRoomBookingResponseDTO create(MeetingRoomBookingRequestDTO request) {
        MeetingRoom room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Meeting room not found: " + request.getRoomId()));

        Employee bookedBy = employeeRepository.findById(request.getBookedByEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getBookedByEmployeeId()));


        if (!bookedBy.isTeamLead()) {
            throw new RuntimeException("Only Team Lead employees are allowed to book meeting rooms.");
        }


        boolean overlappingBookingExists = bookingRepository
                .existsByRoom_RoomIdAndMeetingDateAndStartTimeLessThanAndEndTimeGreaterThan(
                        room.getRoomId(),
                        request.getMeetingDate(),
                        request.getEndTime(),
                        request.getStartTime()
                );

        if (overlappingBookingExists) {
            throw new RuntimeException("Meeting room is already booked for the selected date and time range.");
        }


        boolean alreadyBookedThisRoom = bookingRepository.existsByBookedBy_EmployeeIdAndRoom_RoomId(
                bookedBy.getEmployeeId(), room.getRoomId()
        );


        if (alreadyBookedThisRoom && overlappingBookingExists) {
            throw new RuntimeException("Team Lead has already booked this meeting room for the same time slot.");
        }


        MeetingRoomBooking booking = MeetingRoomBookingMapper.toEntity(request, room, bookedBy);

        booking.setStatus(MeetingRoomBooking.MeetingStatus.BOOKED);
        booking.setActive(true);


        room.setRoomStatus(MeetingRoom.MeetingRoomStatus.NOT_AVAILABLE);
        roomRepository.save(room);

        bookingRepository.save(booking);

        return MeetingRoomBookingMapper.toResponse(booking);
    }


    public MeetingRoomBookingResponseDTO update(String id, MeetingRoomBookingRequestDTO request) {
        MeetingRoomBooking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meeting booking not found: " + id));

        MeetingRoom room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Meeting room not found: " + request.getRoomId()));

        Employee bookedBy = employeeRepository.findById(request.getBookedByEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getBookedByEmployeeId()));

        MeetingRoomBookingMapper.updateEntity(existing, request, room, bookedBy);

        bookingRepository.save(existing);

        return MeetingRoomBookingMapper.toResponse(existing);
    }

    @Override
    public void delete(String id) {
        bookingRepository.deleteById(id);
    }
}