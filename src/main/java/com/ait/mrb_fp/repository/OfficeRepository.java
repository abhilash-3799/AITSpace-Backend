package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, String> {
    List<Office> findByIsActiveTrue();

    boolean existsByOfficeName(String officeName);

    Office findByOfficeName(String officeName);
}
