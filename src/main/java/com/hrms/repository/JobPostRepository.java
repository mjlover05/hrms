package com.hrms.repository;

import com.hrms.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
    JobPost findByTitle(String title);
}
