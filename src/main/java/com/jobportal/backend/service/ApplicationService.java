package com.jobportal.backend.service;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.backend.model.Application;
import com.jobportal.backend.model.Candidate;
import com.jobportal.backend.model.Job;
import com.jobportal.backend.model.Recruiter;
import com.jobportal.backend.repository.ApplicationRepository;
import com.jobportal.backend.repository.CandidateRepository;
import com.jobportal.backend.repository.JobRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    // Apply Job
    public Application applyJob(
            Long jobId,
            MultipartFile file,
            String candidateEmail)
            throws IOException {

        Candidate candidate =

                candidateRepository
                        .findByEmail(candidateEmail)
                        .orElse(null);

        if (candidate == null) {

            return null;
        }

        Job job =

                jobRepository.findById(jobId)
                        .orElse(null);

        if (job == null) {

            return null;
        }

        Recruiter recruiter =
                job.getRecruiter();

        // Upload Folder
        String uploadDir =

                System.getProperty("user.dir")
                        + "/uploads/";

        Files.createDirectories(
                Paths.get(uploadDir));

        // Unique File Name
        String fileName =

                UUID.randomUUID()
                        + "_"
                        + file.getOriginalFilename();

        Path path =
                Paths.get(uploadDir + fileName);

        Files.copy(
                file.getInputStream(),
                path,
                StandardCopyOption.REPLACE_EXISTING);

        // Save Application
        Application application =
                new Application();

        application.setAppliedDate(
                LocalDateTime.now());

        application.setResumeFile(
                fileName);

        application.setStatus(
                "APPLIED");

        application.setCandidate(
                candidate);

        application.setRecruiter(
                recruiter);

        application.setJob(job);

        return applicationRepository
                .save(application);
    }

    // Candidate Own Applications
    public List<Application>
    getCandidateApplications(
            String email) {

        Candidate candidate =

                candidateRepository
                        .findByEmail(email)
                        .orElse(null);

        if (candidate == null) {

            return List.of();
        }

        return applicationRepository
                .findByCandidateId(
                        candidate.getId());
    }

    // Recruiter Applications
    public List<Application>
    getRecruiterApplications(
            String email) {

        List<Application> applications =

                applicationRepository
                        .findAll();

        return applications.stream()

                .filter(app ->

                        app.getRecruiter()
                                .getEmail()
                                .equals(email))

                .toList();
    }
    
 // Update Status
    public Application updateStatus(
            Long applicationId,
            String status,
            String recruiterEmail) {

        Application application =

                applicationRepository
                        .findById(applicationId)
                        .orElse(null);

        if (application == null) {

            return null;
        }

        // Ownership Check
        if (!application.getRecruiter()
                .getEmail()
                .equals(recruiterEmail)) {

            return null;
        }

        application.setStatus(status);

        return applicationRepository
                .save(application);
    }
}