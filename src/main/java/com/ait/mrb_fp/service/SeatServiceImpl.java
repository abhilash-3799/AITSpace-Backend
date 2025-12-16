package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.SeatRequestDTO;
import com.ait.mrb_fp.dto.response.SeatResponseDTO;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.entity.Queue;
import com.ait.mrb_fp.entity.Seat;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.exception.DatabaseException;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.SeatMapper;
import com.ait.mrb_fp.repository.OfficeRepository;
import com.ait.mrb_fp.repository.QueueRepository;
import com.ait.mrb_fp.repository.SeatRepository;
import com.ait.mrb_fp.repository.TeamRepository;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

	private final SeatRepository seatRepo;
	private final OfficeRepository officeRepo;
	private final TeamRepository teamRepo;
	private final QueueRepository queueRepo;

	public SeatServiceImpl(SeatRepository seatRepo, OfficeRepository officeRepo, TeamRepository teamRepo,
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
	public SeatResponseDTO getById(String id) {
		Seat seat = seatRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
		return SeatMapper.toResponse(seat);
	}

	@Override
	public List<SeatResponseDTO> getAll() {
		try {
			return seatRepo.findAll().stream().map(SeatMapper::toResponse).collect(Collectors.toList());

		} catch (DataAccessException ex) {
			throw new DatabaseException("Failed to fetch seats from database.");
		}
	}

	@Override
	public SeatResponseDTO update(String id, SeatRequestDTO dto) {
		Seat existing = seatRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
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
