package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long>

{


    List<Workspace> findByIsDeletedFalse();


   static Boolean existsByName(String name)
   {
       return true;
   };

}
