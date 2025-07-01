package com.hrms.controller;

import com.hrms.model.*;
import com.hrms.repository.ApplicantRepository;
import com.hrms.repository.ComplaintRepository;
import com.hrms.repository.EmployeeRepository;
import com.hrms.repository.PayrollRepository;
import com.hrms.service.ApplicantService;
import com.hrms.service.EmployeeService;
import com.hrms.service.HRService;
import com.hrms.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletionService;

@Controller
@RequestMapping("/hr")
public class HRController {

    @Autowired
    private HRService hrService;

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "hr/dashboard";
    }

    @GetMapping("/logout")
    public String logout() {
        return "index";
    }

    @GetMapping("/employees")
    public String showEmployees(Model model) {
        List<Employee> employees = hrService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "hr/manage-employee";
    }

    @PostMapping("/add-employee")
    public String addEmployee(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam int age,
                              @RequestParam String designation,
                              @RequestParam String previousCompany,
                              @RequestParam String address,
                              @RequestParam String username,
                              @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(RoleType.EMPLOYEE);

        Employee emp = new Employee();
        emp.setName(name);
        emp.setEmail(email);
        emp.setAge(age);
        emp.setDesignation(designation);
        emp.setPreviousCompany(previousCompany);
        emp.setAddress(address);
        emp.setUser(user);

        hrService.addEmployee(emp);
        return "redirect:/hr/employees";
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable int id) {
        hrService.deleteEmployeeById(id);
        return "redirect:/hr/employees";
    }

    // Show edit form
    @GetMapping("/edit-employee/{id}")
    public String showEditEmployeeForm(@PathVariable int id, Model model) {
        Employee employee = hrService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "hr/edit-employee";
    }

    // Handle edit form submission
    @PostMapping("/update-employee")
    public String updateEmployee(@ModelAttribute Employee employee, @RequestParam String username, @RequestParam String password) {
        User user = employee.getUser();
        user.setUsername(username);
        user.setPassword(password);
        employee.setUser(user);

        hrService.updateEmployee(employee);
        return "redirect:/hr/employees";
    }

    @GetMapping("/post-job")
    public String showJobForm(Model model) {
        model.addAttribute("job", new JobPost());
        return "hr/job-form";
    }

    @PostMapping("/post-job")
    public String postJob(@ModelAttribute JobPost jobPost) {
        hrService.saveJob(jobPost);
        return "redirect:/hr/dashboard";
    }

    @GetMapping("/applicants")
    public String viewApplicants(Model model) {
        List<Applicant> applicants = applicantService.getAllApplicants();
        model.addAttribute("applicants", applicants);
        return "hr/view-applicants";
    }


    @GetMapping("/notices")
    public String viewNotices(Model model) {
        model.addAttribute("notices", hrService.getAllNotices());
        model.addAttribute("newNotice", new Notice());
        return "hr/notices";
    }

    @PostMapping("/notices")
    public String postNotice(@ModelAttribute Notice notice) {
        hrService.postNotice(notice);
        return "redirect:/hr/notices";
    }

    @GetMapping("/delete-notice/{id}")
    public String deleteNotice(@PathVariable int id) {
        hrService.deleteNotice(id);
        return "redirect:/hr/notices";
    }

    @GetMapping("/edit-notice/{id}")
    public String showEditNoticeForm(@PathVariable int id, Model model) {
        Notice notice = hrService.getNoticeById(id);
        model.addAttribute("editNotice", notice);
        model.addAttribute("notices", hrService.getAllNotices());
        return "hr/notices";
    }

    @PostMapping("/update-notice")
    public String updateNotice(@ModelAttribute("editNotice") Notice updatedNotice) {
        Notice existing = hrService.getNoticeById(updatedNotice.getId());
        if (existing != null) {
            existing.setTitle(updatedNotice.getTitle());
            existing.setContent(updatedNotice.getContent());
            existing.setDatePosted(LocalDate.now());
            hrService.postNotice(existing);
        }
        return "redirect:/hr/notices";
    }

    @GetMapping("/complaints")
    public String viewComplaints(Model model) {
        List<Complaint> complaints = complaintRepository.findAll(); // or use service layer if preferred
        model.addAttribute("complaints", complaints);
        return "hr/complaints";
    }

    // GET request to show leave list
    @GetMapping("/leaves")
    public String viewAllLeaves(Model model) {
        List<EmployeeLeave> leaves = hrService.getAllLeaveRequests(); // pending + others
        model.addAttribute("leaves", leaves);
        return "hr/leaves";
    }

    // POST request to approve
    @PostMapping("/approve-leave")
    public String approveLeave(@RequestParam("leaveId") int leaveId) {
        hrService.approveLeave(leaveId);
        return "redirect:/hr/leaves";
    }

    // POST request to reject
    @PostMapping("/reject-leave")
    public String rejectLeave(@RequestParam("leaveId") int leaveId) {
        hrService.rejectLeave(leaveId);
        return "redirect:/hr/leaves";
    }





    @Autowired
    private PayrollService payrollService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/payrolls")
    public String showPayrolls(Model model,
                               @ModelAttribute("error") String error,
                               @ModelAttribute("success") String success) {
        List<Payroll> payrolls = payrollService.getAllPayrolls();
        List<Employee> allEmployees = employeeService.getAllEmployees();

        // Remove employees with existing payrolls
        List<Integer> paidEmployeeIds = payrolls.stream()
                .map(p -> p.getEmployee().getId())
                .toList();

        List<Employee> unpaidEmployees = allEmployees.stream()
                .filter(e -> !paidEmployeeIds.contains(e.getId()))
                .toList();

        Payroll payroll = new Payroll();
        payroll.setEmployee(new Employee()); // ✅ Prevent Thymeleaf error

        model.addAttribute("payrolls", payrolls);
        model.addAttribute("employees", unpaidEmployees);
        model.addAttribute("payroll", payroll);

        return "hr/payrolls";
    }



    @PostMapping("/payrolls")
    public String addPayroll(@ModelAttribute Payroll payroll, RedirectAttributes redirectAttributes) {
        String result = payrollService.addPayroll(payroll);

        if ("success".equals(result)) {
            redirectAttributes.addFlashAttribute("success", "Payroll added successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", result);
        }

        return "redirect:/hr/payrolls";
    }




    @GetMapping("/payrolls/edit/{id}")
    public String editPayroll(@PathVariable int id, Model model) {
        Payroll payroll = payrollService.getPayrollById(id);
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("payroll", payroll);
        model.addAttribute("employees", employees);
        model.addAttribute("payrolls", payrollService.getAllPayrolls());
        return "hr/payrolls";
    }

    @GetMapping("/payrolls/delete/{id}")
    public String deletePayroll(@PathVariable int id) {
        payrollService.deletePayroll(id);
        return "redirect:/hr/payrolls";
    }
}




