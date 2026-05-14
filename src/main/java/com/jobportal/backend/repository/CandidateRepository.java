package com.jobportal.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.model.Candidate;

public interface CandidateRepository
extends JpaRepository<Candidate, Long> {

    Optional<Candidate>
    findByEmail(String email);
}