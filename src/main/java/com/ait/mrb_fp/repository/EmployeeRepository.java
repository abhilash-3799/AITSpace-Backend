package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    boolean existsByEmail(String email);
    Employee findByEmail(String email);
    boolean existsByFirstNameAndLastNameAndTeam(String firstName, String lastName, Team team);

    List<Employee> findByIsActiveTrue();
    List<Employee> findByIsActiveFalse();
    List<Employee> findByIsActive(boolean isActive);
    List<Employee> findByTeam(Team team);
    List<Employee> findByOffice(Office office);
    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    List<Employee> findByEmployeeType(Employee.EmployeeType type);

    Long countByIsActiveTrue();
}
