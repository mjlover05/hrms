package com.hrms.service;


import com.hrms.model.Complaint;
import com.hrms.model.Employee;
import com.hrms.model.EmployeeLeave;
import com.hrms.model.Payroll;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee getEmployeeByUsername(String username);
    void raiseComplaint(String username, Complaint complaint);
    List<Complaint> getMyComplaints(String username);


    List<Employee> getAllEmployees();

    EmployeeLeave applyLeave(EmployeeLeave leave);
    List<EmployeeLeave> getLeavesByEmployeeId(int employeeId);

    Employee getEmployeeById(int employeeId);

    Optional<Payroll> viewPayrollByEmployeeId(int employeeId);

// New method


}
