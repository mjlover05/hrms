package com.hrms.service;

import com.hrms.model.JobPost;

import java.util.List;

public interface JobPostService {
   List<JobPost> getAll();

   JobPost getJobById(int jobId);

   JobPost getJobByTitle(String title);
}
