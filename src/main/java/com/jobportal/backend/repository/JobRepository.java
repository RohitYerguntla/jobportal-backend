package com.jobportal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.model.Job;

public interface JobRepository
extends JpaRepository<Job, Long> {

    List<Job>
    findByRecruiterId(Long recruiterId);
}