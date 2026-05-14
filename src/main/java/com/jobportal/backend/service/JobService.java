package com.jobportal.backend.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.backend.model.Job;
import com.jobportal.backend.model.Recruiter;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.RecruiterRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    // Add Job
    public Job addJob(
            Job job,
            String recruiterEmail) {

        Recruiter recruiter =

                recruiterRepository
                        .findByEmail(
                                recruiterEmail)
                        .orElse(null);

        if (recruiter == null) {

            return null;
        }

        job.setRecruiter(recruiter);

        return jobRepository.save(job);
    }

    // All Jobs
    public List<Job> getAllJobs() {

        return jobRepository.findAll();
    }

    // Recruiter Own Jobs
    public List<Job> getRecruiterJobs(
            String email) {

        Recruiter recruiter =

                recruiterRepository
                        .findByEmail(email)
                        .orElse(null);

        if (recruiter == null) {

            return List.of();
        }

        return jobRepository
                .findByRecruiterId(
                        recruiter.getId());
    }

    // Delete Job
    public boolean deleteJob(
            Long jobId,
            String recruiterEmail) {

        Job job =

                jobRepository.findById(jobId)
                        .orElse(null);

        if (job == null) {

            return false;
        }

        // Ownership Check
        if (!job.getRecruiter()
                .getEmail()
                .equals(recruiterEmail)) {

            return false;
        }

        jobRepository.delete(job);

        return true;
    }

    // Update Job
    public Job updateJob(
            Long jobId,
            Job updatedJob,
            String recruiterEmail) {

        Job existingJob =

                jobRepository.findById(jobId)
                        .orElse(null);

        if (existingJob == null) {

            return null;
        }

        // Ownership Check
        if (!existingJob.getRecruiter()
                .getEmail()
                .equals(recruiterEmail)) {

            return null;
        }

        existingJob.setCompanyName(
                updatedJob.getCompanyName());

        existingJob.setTitle(
                updatedJob.getTitle());

        existingJob.setDescription(
                updatedJob.getDescription());

        existingJob.setOpenDate(
                updatedJob.getOpenDate());

        existingJob.setCloseDate(
                updatedJob.getCloseDate());

        return jobRepository.save(existingJob);
    }
}