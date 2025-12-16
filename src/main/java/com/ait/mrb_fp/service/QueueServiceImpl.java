package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.QueueRequestDTO;
import com.ait.mrb_fp.dto.response.QueueResponseDTO;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.entity.Queue;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.QueueMapper;
import com.ait.mrb_fp.repository.OfficeRepository;
import com.ait.mrb_fp.repository.QueueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepo;
    private final OfficeRepository officeRepo;

    public QueueServiceImpl(QueueRepository queueRepo, OfficeRepository officeRepo) {
        this.queueRepo = queueRepo;
        this.officeRepo = officeRepo;
    }

    @Override
    public QueueResponseDTO create(QueueRequestDTO dto) {
        Office office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));
        Queue queue = QueueMapper.toEntity(dto, office);


        queueRepo.save(queue);
        return QueueMapper.toResponse(queue);
    }

    @Override
    public QueueResponseDTO getById(String id) {
        Queue queue = queueRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));
        return QueueMapper.toResponse(queue);
    }

    @Override
    public List<QueueResponseDTO> getAll() {
        return queueRepo.findAll().stream().map(QueueMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public QueueResponseDTO update(String id, QueueRequestDTO dto) {
        Queue existing = queueRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));
        Office office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));
        QueueMapper.updateEntity(existing, dto, office);
        queueRepo.save(existing);
        return QueueMapper.toResponse(existing);
    }

    @Override
    public void delete(String id) {
        queueRepo.deleteById(id);
    }
}
