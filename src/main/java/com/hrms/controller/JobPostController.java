package com.hrms.controller;

import com.hrms.model.Applicant;
import com.hrms.model.JobPost;
import com.hrms.service.ApplicantService;
import com.hrms.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JobPostController {
    @Autowired
    private JobPostService service;
    @Autowired
    private ApplicantService serviceApplicant;


    @GetMapping("/apply")
    public String apply(@RequestParam(value = "submitted", required = false) Boolean submitted, Model model) {
        model.addAttribute("jobs", service.getAll());
        model.addAttribute("submitted", submitted != null && submitted); // pass flag to HTML
        return "apply";
    }



    @GetMapping("/apply-form")
    public String applyForm(@RequestParam("jobTitle") String title, Model model) {
        System.out.println("=== /apply-form called with jobId = " + title);
        JobPost post = service.getJobByTitle(title);

        Applicant applicant = new Applicant();
        applicant.setJobTitle(post.getTitle());

        model.addAttribute("job", post);
        model.addAttribute("applicant", applicant);
        return "application-form";
    }


    @PostMapping("/submit-application")
    public String submitApplication(@ModelAttribute Applicant applicant, Model model) {
        System.out.println(applicant);
        serviceApplicant.addApplicant(applicant);
        return "redirect:/apply?submitted=true";
    }


}
