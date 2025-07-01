package com.hrms.service.impl;

import com.hrms.model.Employee;
import com.hrms.model.Payroll;
import com.hrms.repository.EmployeeRepository;
import com.hrms.repository.PayrollRepository;
import com.hrms.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public String addPayroll(Payroll payroll) {
        int employeeId = payroll.getEmployee().getId();

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            return "Invalid employee ID.";
        }

        if (payrollRepository.existsByEmployeeId(employeeId)) {
            return "Salary for this employee already exists.";
        }

        Employee employee = optionalEmployee.get();
        payroll.setEmployee(employee);

        // Calculate gross and net salary
        double gross = payroll.getBasicSalary() + payroll.getHra() + payroll.getDa();
        double deductions = payroll.getTax() + payroll.getPf() + payroll.getInsurance() + payroll.getOtherDeductions();
        double net = gross - deductions;

        payroll.setGrossSalary(gross);
        payroll.setNetSalary(net);
        payroll.setGeneratedDate(LocalDate.now());

        payrollRepository.save(payroll);
        return "success";
    }

    @Override
    public void deletePayroll(int id) {
        payrollRepository.deleteById(id);
    }

    @Override
    public Payroll getPayrollById(int id) {
        return payrollRepository.findById(id).orElse(null);
    }

    @Override
    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }

    @Override
    public boolean existsByEmployeeId(int employeeId) {
        return payrollRepository.existsByEmployeeId(employeeId);
    }

    @Override
    public void addOrUpdatePayroll(Payroll payroll) {
        payrollRepository.save(payroll);
    }

    @Override
    public Payroll findByEmployeeId(int employeeId) {
        return payrollRepository.findByEmployeeId(employeeId).orElse(null);
    }




}
