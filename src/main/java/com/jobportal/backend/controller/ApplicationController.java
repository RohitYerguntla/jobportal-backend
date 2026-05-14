package com.jobportal.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.backend.model.Application;
import com.jobportal.backend.service.ApplicationService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/applications")
//@CrossOrigin("http://localhost:5173")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // Apply Job
    @PostMapping("/apply/{jobId}")
    public Application applyJob(
            @PathVariable Long jobId,
            @RequestParam("file")
            MultipartFile file,
            Principal principal)
            throws IOException {

        return applicationService.applyJob(
                jobId,
                file,
                principal.getName());
    }

    // Candidate Own Applications
    @GetMapping("/my")
    public List<Application>
    getCandidateApplications(
            Principal principal) {

        return applicationService
                .getCandidateApplications(
                        principal.getName());
    }

    // Recruiter Applications
    @GetMapping("/recruiter")
    public List<Application>
    getRecruiterApplications(
            Principal principal) {

        return applicationService
                .getRecruiterApplications(
                        principal.getName());
    }
    
 // Update Application Status
    @PutMapping("/status/{id}")
    public Application updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            Principal principal) {

        return applicationService
                .updateStatus(
                        id,
                        status,
                        principal.getName());
    }
}