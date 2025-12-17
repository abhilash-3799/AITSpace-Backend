package com.aitspace.service;

import com.aitspace.dto.request.SeatRequestDTO;
import com.aitspace.dto.response.SeatResponseDTO;
import com.aitspace.entity.Office;
import com.aitspace.entity.Queue;
import com.aitspace.entity.Seat;
import com.aitspace.entity.Team;
import com.aitspace.exception.ResourceNotFoundException;
import com.aitspace.mapper.SeatMapper;
import com.aitspace.repository.OfficeRepository;
import com.aitspace.repository.QueueRepository;
import com.aitspace.repository.SeatRepository;
import com.aitspace.repository.TeamRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepo;
    private final OfficeRepository officeRepo;
    private final TeamRepository teamRepo;
    private final QueueRepository queueRepo;

    public SeatServiceImpl(SeatRepository seatRepo,
                           OfficeRepository officeRepo,
                           TeamRepository teamRepo,
                           QueueRepository queueRepo) {
        this.seatRepo = seatRepo;
        this.officeRepo = officeRepo;
        this.teamRepo = teamRepo;
        this.queueRepo = queueRepo;
    }

    @Override
    public SeatResponseDTO create(SeatRequestDTO dto) {
        Office office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

        Team team = teamRepo.findById(dto.getAssignedTeamId()).orElse(null);
        Queue queue = queueRepo.findById(dto.getQueueId()).orElse(null);

        Seat seat = SeatMapper.toEntity(dto, office, team, queue);
        seatRepo.save(seat);

        return SeatMapper.toResponse(seat);
    }

    @Override
    @Transactional(readOnly = true)
    public SeatResponseDTO getById(String id) {
        Seat seat = seatRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        return SeatMapper.toResponse(seat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatResponseDTO> getAll() {
        return seatRepo.findAll()
                .stream()
                .map(SeatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatResponseDTO update(String id, SeatRequestDTO dto) {
        Seat existing = seatRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        Office office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

        Team team = teamRepo.findById(dto.getAssignedTeamId()).orElse(null);
        Queue queue = queueRepo.findById(dto.getQueueId()).orElse(null);

        SeatMapper.updateEntity(existing, dto, office, team, queue);
        seatRepo.save(existing);

        return SeatMapper.toResponse(existing);
    }

    @Override
    public void delete(String id) {
        seatRepo.deleteById(id);
    }
}
