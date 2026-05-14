package com.jobportal.backend.model;

//package com.jobportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "recruiters")
public class Recruiter extends User {
}