package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, String> {
    List<Team> findByIsActiveTrue();

    boolean existsByTeamName(String teamName);

    Team findByTeamName(String teamName);
}

