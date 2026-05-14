package com.jobportal.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.dto.LoginRequest;
import com.jobportal.backend.dto.LoginResponse;
import com.jobportal.backend.dto.RegisterRequest;
import com.jobportal.backend.model.Candidate;
import com.jobportal.backend.model.Recruiter;
import com.jobportal.backend.security.JwtUtil;
import com.jobportal.backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin("http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Register
    @PostMapping("/register")
    public Object register(
            @RequestBody RegisterRequest request) {

        return userService.registerUser(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole());
    }

    // Login
    @PostMapping("/login")
public ResponseEntity<?> login(
        @RequestBody LoginRequest request) {

    Object user =

            userService.loginUser(
                    request.getEmail(),
                    request.getPassword(),
                    request.getRole());

    // Candidate
    if (user instanceof Candidate candidate) {

        String token =

                jwtUtil.generateToken(
                        candidate.getEmail(),
                        candidate.getRole());

        return ResponseEntity.ok(

                new LoginResponse(
                        token,
                        candidate.getName(),
                        candidate.getRole()));
    }

    // Recruiter
    if (user instanceof Recruiter recruiter) {

        String token =

                jwtUtil.generateToken(
                        recruiter.getEmail(),
                        recruiter.getRole());

        return ResponseEntity.ok(

                new LoginResponse(
                        token,
                        recruiter.getName(),
                        recruiter.getRole()));
    }

    // Invalid Login
    return ResponseEntity
            .status(401)
            .body("Invalid Email, Password or Role");
        }
}