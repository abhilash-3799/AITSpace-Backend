package com.aitspace.repository;

import com.aitspace.entity.Employee;
import com.aitspace.entity.Team;
import com.aitspace.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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





    @Query("""
       SELECT e FROM Employee e
       LEFT JOIN FETCH e.team
       LEFT JOIN FETCH e.office
       """)
    List<Employee> findAllWithRelations();


    @Query("""
       SELECT e FROM Employee e
       LEFT JOIN FETCH e.team
       LEFT JOIN FETCH e.office
       WHERE e.employeeId = :id
       """)
    Employee findByIdWithRelations(@Param("id")String id);
}


