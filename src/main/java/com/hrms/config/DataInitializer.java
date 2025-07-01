//package com.hrms.config;
//
//import com.hrms.model.RoleType;
//import com.hrms.model.User;
//import com.hrms.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//
//@Component
//public class DataInitializer {
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void insertDefaultHR() {
//        String defaultUsername = "manish";
//        String defaultPassword = "admin123"; // You can customize this
//
//        if (userRepo.findByUsername(defaultUsername).isEmpty()) {
//            User hr = new User();
//            hr.setUsername(defaultUsername);
//            hr.setPassword(passwordEncoder.encode(defaultPassword));
//            hr.setRole(RoleType.HR); // Assuming enum
//            hr.setEnabled(true);
//
//            userRepo.save(hr);
//            System.out.println("✅ Default HR user created: " + defaultUsername);
//        } else {
//            System.out.println("⚠️ HR user already exists. Skipping creation.");
//        }
//    }
//}
