package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.OfficeRequestDTO;
import com.ait.mrb_fp.dto.response.OfficeResponseDTO;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.exception.*;
import com.ait.mrb_fp.mapper.OfficeMapper;
import com.ait.mrb_fp.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;


    @Override
    public OfficeResponseDTO createOffice(OfficeRequestDTO dto) {
        try {
            if (dto == null) {
                throw new InvalidRequestBodyException("Office request body cannot be null.");
            }

            if (dto.getOfficeName() == null || dto.getOfficeName().isBlank()) {
                throw new MissingRequestParameterException("Office name is required.");
            }

            if (dto.getLocation() == null || dto.getLocation().isBlank()) {
                throw new MissingRequestParameterException("Office location is required.");
            }


            boolean exists = officeRepository.existsByOfficeName(dto.getOfficeName());
            if (exists) {
                throw new DuplicateResourceException("Office with name already exists: " + dto.getOfficeName());
            }

            Office office = OfficeMapper.toEntity(dto);

            office.setActive(true);

            return OfficeMapper.toResponse(officeRepository.save(office));

        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred while creating office.");
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed while creating office.");
        }
    }


    @Override
    public OfficeResponseDTO getOfficeById(String officeId) {
        if (officeId == null || officeId.isBlank()) {
            throw new MissingRequestParameterException("Office ID cannot be empty.");
        }

        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));

        if (!office.isActive()) {
            throw new InvalidStateException("Office is inactive. Reactivate before accessing details.");
        }

        return OfficeMapper.toResponse(office);
    }


    @Override
    public List<OfficeResponseDTO> getAllOffices() {
        try {
            return officeRepository.findByIsActiveTrue()
                    .stream()
                    .map(OfficeMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseException("Error fetching offices from database.");
        }
    }


    @Override
    public OfficeResponseDTO updateOffice(String officeId, OfficeRequestDTO dto) {
        try {
            if (officeId == null || officeId.isBlank()) {
                throw new MissingRequestParameterException("Office ID is required for update.");
            }

            if (dto == null) {
                throw new InvalidRequestBodyException("Office update request cannot be null.");
            }

            Office existing = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));

            if (!existing.isActive()) {
                throw new InvalidStateException("Cannot update an inactive office.");
            }

            Office duplicate = officeRepository.findByOfficeName(dto.getOfficeName());
            if (duplicate != null && !duplicate.getOfficeId().equals(officeId)) {
                throw new DuplicateResourceException("Another office already exists with name: " + dto.getOfficeName());
            }

            existing.setOfficeName(dto.getOfficeName());
            existing.setLocation(dto.getLocation());
            existing.setTotalSeats(dto.getTotalSeats());

            return OfficeMapper.toResponse(officeRepository.save(existing));

        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred while updating office.");
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed during office update.");
        }
    }


    @Override
    public void deactivateOffice(String officeId) {
        try {
            if (officeId == null || officeId.isBlank()) {
                throw new MissingRequestParameterException("Office ID is required for deactivation.");
            }

            Office office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));

            if (!office.isActive()) {
                throw new InvalidStateException("Office is already inactive.");
            }

            office.setActive(false);
            officeRepository.save(office);

        } catch (DataAccessException ex) {
            throw new DatabaseException("Error occurred while deactivating office in database.");
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed while deactivating office.");
        }
    }
}
