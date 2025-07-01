package com.hrms.service;

import com.hrms.model.Applicant;

import java.util.List;

public interface ApplicantService {
    void addApplicant(Applicant applicant);

    List<Applicant> getAllApplicants();
}
