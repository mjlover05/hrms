package com.hrms.repository;

import com.hrms.model.Employee;
import com.hrms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Optional<Employee> findByUserUsername(String username);
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByUser(User user);

}
