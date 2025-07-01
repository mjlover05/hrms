package com.hrms.service;

import com.hrms.model.*;

import java.util.List;

public interface HRService {
    List<Employee> getAllEmployees();
    void addEmployee(Employee employee);
    Employee getEmployeeById(int id);
    void deleteEmployeeById(int id);
    void updateEmployee(Employee employee);

    void saveJob(JobPost jobPost);
    void postNotice(Notice notice);
    List<Notice> getAllNotices();
    void deleteNotice(int id);
    Notice getNoticeById(int id);

    void approveLeave(int leaveId);
    void rejectLeave(int leaveId);
    List<EmployeeLeave> getAllLeaveRequests();




}
