package com.jobportal.backend.config;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.*;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.jobportal.backend.security.JwtFilter;
//
//@Configuration
//public class SecurityConfig {
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(
//            HttpSecurity http)
//            throws Exception {
//
//        http
//
//                .csrf(csrf -> csrf.disable())
//
//                .authorizeHttpRequests(auth -> auth
//
//                        .requestMatchers(
//                                "/api/auth/**")
//                        .permitAll()
//
//                        .requestMatchers(
//                                "/api/jobs")
//                        .hasRole("CANDIDATE")
//
//                        .requestMatchers(
//                                "/api/jobs/my-jobs")
//                        .hasRole("RECRUITER")
//
//                        .requestMatchers(
//                                "/api/jobs/add")
//                        .hasRole("RECRUITER")
//
//                        .requestMatchers(
//                                "/api/jobs/update/**")
//                        .hasRole("RECRUITER")
//
//                        .requestMatchers(
//                                "/api/jobs/delete/**")
//                        .hasRole("RECRUITER")
//
//                        .requestMatchers(
//                                "/api/applications/apply/**")
//                        .hasRole("CANDIDATE")
//
//                        .requestMatchers(
//                                "/api/applications/my")
//                        .hasRole("CANDIDATE")
//
//                        .requestMatchers(
//                                "/api/applications/recruiter")
//                        .hasRole("RECRUITER")
//
//                        .requestMatchers(
//                                "/api/resume/**")
//                        .authenticated()
//
//                        .anyRequest()
//                        .authenticated())
//
//                .sessionManagement(session ->
//
//                        session.sessionCreationPolicy(
//                                SessionCreationPolicy.STATELESS))
//
//                .addFilterBefore(
//                        jwtFilter,
//                        UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}



import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jobportal.backend.security.JwtFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(
            JwtFilter jwtFilter) {

        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

                .cors(cors -> {})

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->

                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/api/auth/**")
                        .permitAll()

                        .requestMatchers(
                                "/api/jobs")
                        .hasRole("CANDIDATE")

                        .requestMatchers(
                                "/api/jobs/my-jobs")
                        .hasRole("RECRUITER")

                        .requestMatchers(
                                "/api/jobs/add")
                        .hasRole("RECRUITER")

                        .requestMatchers(
                                "/api/jobs/update/**")
                        .hasRole("RECRUITER")

                        .requestMatchers(
                                "/api/jobs/delete/**")
                        .hasRole("RECRUITER")

                        .requestMatchers(
                                "/api/applications/apply/**")
                        .hasRole("CANDIDATE")

                        .requestMatchers(
                                "/api/applications/my")
                        .hasRole("CANDIDATE")

                        .requestMatchers(
                                "/api/applications/recruiter")
                        .hasRole("RECRUITER")
                        
                        .requestMatchers(
                        	    "/api/applications/status/**")
                        	.hasRole("RECRUITER")

                        .requestMatchers(
                                "/api/resume/**")
//                        .permitAll()
//                        .hasRole("CANDIDATE")
//                        .hasRole("RECRUITER")
                        .authenticated()

                        .anyRequest()
                        .authenticated())

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS
    @Bean
    public CorsConfigurationSource
    corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(

                List.of(
                        "http://localhost:5173"));

        configuration.setAllowedMethods(

                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"));

        configuration.setAllowedHeaders(

                List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =

                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration);

        return source;
    }
}