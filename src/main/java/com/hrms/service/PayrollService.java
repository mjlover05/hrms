package com.hrms.service;

import com.hrms.model.Payroll;

import java.util.List;
import java.util.Optional;

public interface PayrollService {
    void addOrUpdatePayroll(Payroll payroll);
    void deletePayroll(int id);
    Payroll getPayrollById(int id);
    List<Payroll> getAllPayrolls();
    boolean existsByEmployeeId(int employeeId);

    String addPayroll(Payroll payroll);

    Payroll findByEmployeeId(int employeeId);

}

