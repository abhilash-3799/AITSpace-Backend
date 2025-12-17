package com.aitspace.service;

import com.aitspace.dto.request.MeetingRoomRequestDTO;
import com.aitspace.dto.response.MeetingRoomResponseDTO;
import com.aitspace.entity.MeetingRoom;
import com.aitspace.entity.Office;
import com.aitspace.mapper.MeetingRoomMapper;
import com.aitspace.repository.MeetingRoomRepository;
import com.aitspace.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final OfficeRepository officeRepository;

    public MeetingRoomServiceImpl(MeetingRoomRepository meetingRoomRepository, OfficeRepository officeRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
        this.officeRepository = officeRepository;
    }

    @Override
    public List<MeetingRoomResponseDTO> getAll() {
        return meetingRoomRepository.findAll()
                .stream()
                .map(MeetingRoomMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingRoomResponseDTO getById(String id) {
        MeetingRoom room = meetingRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meeting Room not found: " + id));
        return MeetingRoomMapper.toResponse(room);
    }

    @Override
    public MeetingRoomResponseDTO create(MeetingRoomRequestDTO request) {
        Office office = officeRepository.findById(request.getOfficeId())
                .orElseThrow(() -> new RuntimeException("Office not found: " + request.getOfficeId()));
        MeetingRoom meetingRoom = MeetingRoomMapper.toEntity(request, office);

        meetingRoomRepository.save(meetingRoom);
        return MeetingRoomMapper.toResponse(meetingRoom);
    }

    @Override
    public MeetingRoomResponseDTO update(String id, MeetingRoomRequestDTO request) {
        MeetingRoom existing = meetingRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meeting Room not found: " + id));

        Office office = officeRepository.findById(request.getOfficeId())
                .orElseThrow(() -> new RuntimeException("Office not found: " + request.getOfficeId()));

        MeetingRoomMapper.updateEntity(existing, request, office);
        meetingRoomRepository.save(existing);
        return MeetingRoomMapper.toResponse(existing);
    }

    @Override
    public void delete(String id) {
        meetingRoomRepository.deleteById(id);
    }
}
