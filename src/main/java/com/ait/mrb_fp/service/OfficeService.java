package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.OfficeRequestDTO;
import com.ait.mrb_fp.dto.response.OfficeResponseDTO;

import java.util.List;

public interface OfficeService {
    OfficeResponseDTO createOffice(OfficeRequestDTO dto);

    OfficeResponseDTO getOfficeById(String officeId);

    List<OfficeResponseDTO> getAllOffices();

    OfficeResponseDTO updateOffice(String officeId, OfficeRequestDTO dto);

    void deactivateOffice(String officeId);
}
