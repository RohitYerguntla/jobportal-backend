package com.jobportal.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobportal.backend.model.Candidate;
import com.jobportal.backend.model.Recruiter;
import com.jobportal.backend.repository.CandidateRepository;
import com.jobportal.backend.repository.RecruiterRepository;

@Service
public class UserService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    private BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    // Register
    public Object registerUser(
            String name,
            String email,
            String password,
            String role) {

        String encodedPassword =
                passwordEncoder.encode(password);

        // Candidate
        if (role.equals("CANDIDATE")) {

            Candidate candidate =
                    new Candidate();

            candidate.setName(name);

            candidate.setEmail(email);

            candidate.setPassword(
                    encodedPassword);

            candidate.setRole(role);

            candidate.setCreatedAt(
                    LocalDateTime.now());

            return candidateRepository
                    .save(candidate);
        }

        // Recruiter
        else {

            Recruiter recruiter =
                    new Recruiter();

            recruiter.setName(name);

            recruiter.setEmail(email);

            recruiter.setPassword(
                    encodedPassword);

            recruiter.setRole(role);

            recruiter.setCreatedAt(
                    LocalDateTime.now());

            return recruiterRepository
                    .save(recruiter);
        }
    }

    // Login
    public Object loginUser(
            String email,
            String password,
            String role) {

        // Candidate Login
        if (role.equals("CANDIDATE")) {

            Optional<Candidate> optionalCandidate =

                    candidateRepository
                            .findByEmail(email);

            if (optionalCandidate.isPresent()) {

                Candidate candidate =
                        optionalCandidate.get();

                boolean match =

                        passwordEncoder.matches(
                                password,
                                candidate.getPassword());

                if (match) {

                    return candidate;
                }
            }
        }

        // Recruiter Login
        if (role.equals("RECRUITER")) {

            Optional<Recruiter> optionalRecruiter =

                    recruiterRepository
                            .findByEmail(email);

            if (optionalRecruiter.isPresent()) {

                Recruiter recruiter =
                        optionalRecruiter.get();

                boolean match =

                        passwordEncoder.matches(
                                password,
                                recruiter.getPassword());

                if (match) {

                    return recruiter;
                }
            }
        }

        return null;
    }
}