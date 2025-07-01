package com.hrms.service.impl;

import com.hrms.model.*;
import com.hrms.repository.*;
import com.hrms.service.HRService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HRServiceImpl implements HRService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PayrollRepository payrollRepo;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

//    @Override
//    public void addEmployee(Employee employee) {
//        // Set role explicitly
//        if (employee.getUser() != null) {
//            employee.getUser().setRole(RoleType.EMPLOYEE);
//        }
//        employeeRepo.save(employee);
//    }

    @Override
    public void addEmployee(Employee employee) {
        if (employee.getUser() != null) {
            employee.getUser().setRole(RoleType.EMPLOYEE);

            // ✅ Encrypt password
            String encodedPassword = passwordEncoder.encode(employee.getUser().getPassword());
            employee.getUser().setPassword(encodedPassword);
        }

        employeeRepo.save(employee);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public void deleteEmployeeById(int id) {
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (emp.getUser() != null) {
            userRepo.deleteById(emp.getUser().getId());
        }
        employeeRepo.deleteById(id);
    }


    @Override
    @Transactional
    public void updateEmployee(Employee employee) {
        User existingUser = userRepo.findById(employee.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Only update username
        existingUser.setUsername(employee.getUser().getUsername());

        // Only update password if new one is entered
        String newPassword = employee.getUser().getPassword();
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }

        userRepo.save(existingUser);
        employee.setUser(existingUser);
        employeeRepo.save(employee);
    }


    @Autowired
    private JobPostRepository jobRepository;

    @Override
    public void saveJob(JobPost jobPost) {
        jobRepository.save(jobPost);
    }

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public void postNotice(Notice notice) {
        notice.setDatePosted(LocalDate.now());
        noticeRepository.save(notice);
    }

    @Override
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "datePosted"));
    }

    @Override
    public void deleteNotice(int id) {
        noticeRepository.deleteById(id);
    }

    @Override
    public Notice getNoticeById(int id) {
        return noticeRepository.findById(id).orElse(null);
    }

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void approveLeave(int leaveId) {
        EmployeeLeave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found with ID: " + leaveId));
        leave.setStatus(LeaveStatus.APPROVED);
        leaveRepository.save(leave);
    }

    @Override
    public void rejectLeave(int leaveId) {
        EmployeeLeave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found with ID: " + leaveId));
        leave.setStatus(LeaveStatus.REJECTED);
        leaveRepository.save(leave);
    }

    @Override
    public List<EmployeeLeave> getAllLeaveRequests() {
        return leaveRepository.findByStatus(LeaveStatus.PENDING);
    }





}

