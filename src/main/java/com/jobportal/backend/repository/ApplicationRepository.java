package com.jobportal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.model.Application;

public interface ApplicationRepository
extends JpaRepository<Application, Long> {

    List<Application>
    findByCandidateId(Long candidateId);

    List<Application>
    findByRecruiterId(Long recruiterId);

    void deleteByJobId(Long jobId);
}