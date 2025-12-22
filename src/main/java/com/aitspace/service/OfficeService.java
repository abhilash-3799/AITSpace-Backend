package com.aitspace.service;

import com.aitspace.dto.request.OfficeRequestDTO;
import com.aitspace.dto.response.OfficeResponseDTO;

import java.util.List;

public interface OfficeService {
    OfficeResponseDTO createOffice(OfficeRequestDTO dto);

    OfficeResponseDTO getOfficeById(String officeId);

    List<OfficeResponseDTO> getAllOffices();

    OfficeResponseDTO updateOffice(String officeId, OfficeRequestDTO dto);

    void deactivateOffice(String officeId);
}
