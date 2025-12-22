package com.aitspace.service;

import com.aitspace.dto.request.NotificationRequestDTO;
import com.aitspace.dto.request.SeatRequestDTO;
import com.aitspace.dto.response.SeatResponseDTO;
import com.aitspace.entity.*;
import com.aitspace.entity.*;
import com.aitspace.exception.ResourceNotFoundException;
import com.aitspace.mapper.SeatMapper;
import com.aitspace.repository.*;
import com.aitspace.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepo;
    private final OfficeRepository officeRepo;
    private final TeamRepository teamRepo;
    private final QueueRepository queueRepo;
    private final NotificationService notificationService;
    private final EmployeeRepository employeeRepo;


    public SeatServiceImpl(SeatRepository seatRepo, OfficeRepository officeRepo,
                           TeamRepository teamRepo, QueueRepository queueRepo ,NotificationService notificationService,
                           EmployeeRepository employeeRepo) {
        this.seatRepo = seatRepo;
        this.officeRepo = officeRepo;
        this.teamRepo = teamRepo;
        this.queueRepo = queueRepo;
        this.notificationService = notificationService;
        this.employeeRepo = employeeRepo;

    }

    @Override
    public SeatResponseDTO create(SeatRequestDTO dto) {
        Office office = officeRepo.findById(dto.getOfficeId()).orElseThrow(() -> new ResourceNotFoundException("Office not found"));
        Team team = teamRepo.findById(dto.getAssignedTeamId()).orElse(null);
        Queue queue = queueRepo.findById(dto.getQueueId()).orElse(null);
        Seat seat = SeatMapper.toEntity(dto, office, team, queue);


        seatRepo.save(seat);
        if(seat.isAvailable())
        {
            notifySeatAvalaible(seat);
        }
        return SeatMapper.toResponse(seat);
    }




    @Override
    public SeatResponseDTO getById(String id) {
        Seat seat = seatRepo.findByIdWithRelations(id);

        if (seat == null) {
            throw new ResourceNotFoundException("Seat not found");
        }

        return SeatMapper.toResponse(seat);
    }



@Override
public List<SeatResponseDTO> getAll() {
    return seatRepo.findAllWithRelations()
            .stream()
            .map(SeatMapper::toResponse)
            .collect(Collectors.toList());
}




    @Override
    public SeatResponseDTO update(String id, SeatRequestDTO dto) {
        Seat existing = seatRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        boolean wasAvailable= existing.isAvailable();
        Office office = officeRepo.findById(dto.getOfficeId()).orElseThrow(() -> new ResourceNotFoundException("Office not found"));
        Team team = teamRepo.findById(dto.getAssignedTeamId()).orElse(null);
        Queue queue = queueRepo.findById(dto.getQueueId()).orElse(null);
        SeatMapper.updateEntity(existing, dto, office, team, queue);
        seatRepo.save(existing);
        if(!wasAvailable && existing.isAvailable())
        {
            notifySeatAvalaible(existing);
        }
        return SeatMapper.toResponse(existing);
    }

    @Override
    public void delete(String id)
    {
        seatRepo.deleteById(id);
    }
    private void notifySeatAvalaible(Seat seat)
    {
         String message="Seat "+seat.getSeatNumber()+" is now available";
         List<Employee> employees =employeeRepo.findAll();
        employees.forEach(emp -> {
            NotificationRequestDTO dto = NotificationRequestDTO.builder()
                    .employeeId(emp.getEmployeeId())
                    .title("Seat Available")
                    .message(message)
                    .build();

            notificationService.create(dto);
        });
    }
}
