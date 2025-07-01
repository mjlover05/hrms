package com.hrms.service.impl;

import com.hrms.model.Complaint;
import com.hrms.model.Employee;
import com.hrms.model.EmployeeLeave;
import com.hrms.model.Payroll;
import com.hrms.repository.ComplaintRepository;
import com.hrms.repository.EmployeeRepository;
import com.hrms.repository.LeaveRepository;
import com.hrms.repository.PayrollRepository;
import com.hrms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public void raiseComplaint(String username, Complaint complaint) {
        Employee emp = employeeRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        complaint.setEmployee(emp);
        complaintRepository.save(complaint);
    }

    @Override
    public List<Complaint> getMyComplaints(String username) {
        Employee emp = employeeRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return complaintRepository.findByEmployeeId(emp.getId());
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }


    @Autowired
    private LeaveRepository leaveRepository;


    @Override
    public EmployeeLeave applyLeave(EmployeeLeave leave) {
        if (leave.getEmployee() == null || leave.getEmployee().getId() <= 0) {
            throw new RuntimeException("Invalid Employee ID");
        }
        return leaveRepository.save(leave);
    }

    @Override
    public List<EmployeeLeave> getLeavesByEmployeeId(int employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    public Employee getEmployeeById(int employeeId){
        return employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
    }


    @Autowired
    private PayrollRepository payrollRepository;

    @Override
    public Optional<Payroll> viewPayrollByEmployeeId(int employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }







}
