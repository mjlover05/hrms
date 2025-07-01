package com.hrms.controller;

import com.hrms.model.*;
import com.hrms.repository.EmployeeRepository;
import com.hrms.repository.PayrollRepository;
import com.hrms.repository.UserRepository;
import com.hrms.service.EmployeeService;
import com.hrms.service.HRService;
import com.hrms.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    HRService hrService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayrollRepository payrollRepository;


    @GetMapping("/dashboard")
    public String dashbaord() {
        return "employee/dashboard";
    }

    @GetMapping("/notices")
    public String viewNotices(Model model) {
        List<Notice> notices = hrService.getAllNotices(); // assuming HRService is injected
        model.addAttribute("notices", notices);
        return "employee/notices";
    }

    @GetMapping("/profile")
    public String viewProfile(Principal principal, Model model) {
        String username = principal.getName();
        Employee employee = employeeService.getEmployeeByUsername(username);
        model.addAttribute("employee", employee);
        return "employee/profile";
    }

    @GetMapping("/complaint")
    public String showComplaintForm(Model model) {
        model.addAttribute("complaint", new Complaint());
        return "employee/complaint-form";
    }

    @PostMapping("/complaint")
    public String submitComplaint(@ModelAttribute Complaint complaint, Principal principal) {
        employeeService.raiseComplaint(principal.getName(), complaint);
        return "redirect:/employee/dashboard?success";
    }



    @PostMapping("/apply-leave")
    public String applyLeave(@ModelAttribute EmployeeLeave leave, Principal principal, RedirectAttributes redirectAttributes) {
        Employee emp = employeeService.getEmployeeByUsername(principal.getName());
        leave.setEmployee(emp);
        employeeService.applyLeave(leave);
        redirectAttributes.addFlashAttribute("message", "Leave applied successfully!");
        return "redirect:/employee/leaves";
    }

    @GetMapping("/leaves")
    public String viewMyLeaves(Model model, Principal principal) {
        Employee emp = employeeService.getEmployeeByUsername(principal.getName());
        List<EmployeeLeave> leaves = employeeService.getLeavesByEmployeeId(emp.getId());
        model.addAttribute("leaves", leaves);
        return "employee/leave-request";
    }

    @GetMapping("/salary")
    public String viewSalarySlip(Model model, Principal principal) {
        String username = principal.getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Payroll payroll = payrollRepository.findByEmployee(employee)
                .orElse(null); // or throw if required

        model.addAttribute("employee", employee);
        model.addAttribute("payroll", payroll);

        return "employee/salary-slip";
    }






}
