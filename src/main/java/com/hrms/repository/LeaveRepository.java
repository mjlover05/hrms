package com.hrms.repository;

import com.hrms.model.EmployeeLeave;
import com.hrms.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<EmployeeLeave, Integer> {
    List<EmployeeLeave> findByEmployeeId(int employeeId);

    List<EmployeeLeave> findByStatus(LeaveStatus leaveStatus);
}
