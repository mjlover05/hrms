package com.hrms.controller;

import com.hrms.model.RoleType;
import com.hrms.model.User;
import com.hrms.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    // Show login page
    @GetMapping({"/", "/login"})
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {

        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }

        return "index";
    }

    // Redirect based on role after login
    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_HR"))) {
            return "redirect:/hr/dashboard";
        }
        return "redirect:/employee/dashboard";
    }

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register-hr")
    public String registerHr(HttpServletRequest request) {
        if (userRepo.findByUsername("hr").isEmpty()) {
            User hrUser = new User();
            hrUser.setUsername("hr");
            hrUser.setPassword(passwordEncoder.encode("hr"));
            hrUser.setRole(RoleType.HR);
            hrUser.setEnabled(true);
            userRepo.save(hrUser);
            System.out.println("Saved user: " + hrUser.getUsername());
            System.out.println("Saved user: " + hrUser.getUsername());
            request.setAttribute("msg", "HR registered successfully.");
        } else {
            request.setAttribute("msg", "HR already exists.");
        }

        return "redirect:/login";
    }
}
