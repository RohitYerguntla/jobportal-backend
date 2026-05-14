package com.jobportal.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.jobportal.backend.model.Candidate;
import com.jobportal.backend.model.Recruiter;
import com.jobportal.backend.repository.CandidateRepository;
import com.jobportal.backend.repository.RecruiterRepository;

@Service
public class CustomUserDetailsService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    public UserDetails loadUser(
            String email,
            String role) {

        // Candidate
        if (role.equals("CANDIDATE")) {

            Candidate candidate =
                    candidateRepository
                            .findByEmail(email)
                            .orElse(null);

            if (candidate != null) {

                return org.springframework.security.core.userdetails.User

                        .withUsername(
                                candidate.getEmail())

                        .password(
                                candidate.getPassword())

                        .roles(
                                candidate.getRole())

                        .build();
            }
        }

        // Recruiter
        if (role.equals("RECRUITER")) {

            Recruiter recruiter =
                    recruiterRepository
                            .findByEmail(email)
                            .orElse(null);

            if (recruiter != null) {

                return org.springframework.security.core.userdetails.User

                        .withUsername(
                                recruiter.getEmail())

                        .password(
                                recruiter.getPassword())

                        .roles(
                                recruiter.getRole())

                        .build();
            }
        }

        throw new UsernameNotFoundException(
                "User not found");
    }
}