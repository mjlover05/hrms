package com.hrms.service.impl;

import com.hrms.model.JobPost;
import com.hrms.model.Payroll;
import com.hrms.repository.JobPostRepository;
import com.hrms.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPostServiceImpl implements JobPostService {
    @Autowired
    private JobPostRepository repo;

    @Override
    public List<JobPost> getAll() {
        return repo.findAll();
    }

    @Override
    public JobPost getJobById(int id) {
        return repo.findById(id).get();
    }

    @Override
    public  JobPost getJobByTitle(String title) {
        return repo.findByTitle(title);
    }





}
