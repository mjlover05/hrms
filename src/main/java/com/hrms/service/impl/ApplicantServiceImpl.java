package com.hrms.service.impl;

import com.hrms.model.Applicant;
import com.hrms.repository.ApplicantRepository;
import com.hrms.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {
    @Autowired
    private ApplicantRepository applicantRepository;
    @Override
    public void addApplicant(Applicant applicant){
        applicantRepository.save(applicant);
    }

    @Override
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }
}
