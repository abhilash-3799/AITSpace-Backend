package com.aitspace.service;

import com.aitspace.dto.request.QueueRequestDTO;
import com.aitspace.dto.response.QueueResponseDTO;
import com.aitspace.entity.Office;
import com.aitspace.entity.Queue;
import com.aitspace.exception.ResourceNotFoundException;
import com.aitspace.mapper.QueueMapper;
import com.aitspace.repository.OfficeRepository;
import com.aitspace.repository.QueueRepository;
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

//    @Override
//    public QueueResponseDTO getById(String id) {
//        Queue queue = queueRepo.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));
//        return QueueMapper.toResponse(queue);
//    }
@Override
public QueueResponseDTO getById(String id) {
    Queue queue = queueRepo.findByIdWithRelations(id);

    if (queue == null) {
        throw new ResourceNotFoundException("Queue not found");
    }

    return QueueMapper.toResponse(queue);
}

//    @Override
//    public List<QueueResponseDTO> getAll() {
//        return queueRepo.findAll().stream().map(QueueMapper::toResponse).collect(Collectors.toList());
//    }
@Override
public List<QueueResponseDTO> getAll() {
    return queueRepo.findAllWithRelations()
            .stream()
            .map(QueueMapper::toResponse)
            .collect(Collectors.toList());
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
