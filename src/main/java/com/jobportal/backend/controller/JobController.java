package com.jobportal.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.model.Job;
import com.jobportal.backend.service.JobService;

@RestController
@RequestMapping("/api/jobs")
//@CrossOrigin("http://localhost:5173")
public class JobController {

    @Autowired
    private JobService jobService;

    // Add Job
    @PostMapping("/add")
    public Job addJob(
            @RequestBody Job job,
            Principal principal) {

        return jobService.addJob(
                job,
                principal.getName());
    }

    // Candidate All Jobs
    @GetMapping
    public List<Job> getAllJobs() {

        return jobService.getAllJobs();
    }

    // Recruiter Own Jobs
    @GetMapping("/my-jobs")
    public List<Job> getRecruiterJobs(
            Principal principal) {

        return jobService.getRecruiterJobs(
                principal.getName());
    }

    // Delete Job
    @DeleteMapping("/delete/{id}")
    public String deleteJob(
            @PathVariable Long id,
            Principal principal) {

        boolean deleted =

                jobService.deleteJob(
                        id,
                        principal.getName());

        if (deleted) {

            return "Job Deleted";
        }

        return "Unauthorized Delete";
    }

    // Update Job
    @PutMapping("/update/{id}")
    public Job updateJob(
            @PathVariable Long id,
            @RequestBody Job job,
            Principal principal) {

        return jobService.updateJob(
                id,
                job,
                principal.getName());
    }
}