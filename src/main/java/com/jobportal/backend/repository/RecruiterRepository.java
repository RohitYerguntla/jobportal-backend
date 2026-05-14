package com.jobportal.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.model.Recruiter;

public interface RecruiterRepository
extends JpaRepository<Recruiter, Long> {

    Optional<Recruiter>
    findByEmail(String email);
}