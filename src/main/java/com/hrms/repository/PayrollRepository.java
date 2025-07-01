package com.hrms.repository;

import com.hrms.model.Employee;
import com.hrms.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Integer> {
    boolean existsByEmployeeId(int employeeId);
    Optional<Payroll> findByEmployeeId(int employeeId);


    Optional<Payroll> findByEmployee(Employee employee);
}


