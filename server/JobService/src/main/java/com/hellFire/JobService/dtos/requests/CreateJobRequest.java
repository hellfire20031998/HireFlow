package com.hellFire.JobService.dtos.requests;

import com.hellFire.JobService.models.enums.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateJobRequest {
    private String title;
    private String description;
    private String aboutRole;
    private Department department;
    private String location;
    private LocationType locationType;
    private JobType jobType;
    private EmploymentType employmentType;
    private SeniorityLevel seniorityLevel;
    private Industry industry;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minSalary;
    private Integer maxSalary;
    private Integer openings;
    private LocalDate deadline;
    private JobStatus status;

}
