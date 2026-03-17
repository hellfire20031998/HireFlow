package com.hellFire.JobService.dtos.requests;

import com.hellFire.JobService.models.enums.*;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class UpdateJobRequest {
    private String title;
    private String description;
    private String aboutRole;
    private Department department;
    private String location;
    private LocationType locationType;
    private JobType jobType;
    private EmploymentType employmentType;
    private SeniorityLevel seniorityLevel;
    private Set<Industry> industries;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minSalary;
    private Integer maxSalary;
    private SalaryCurrency salaryCurrency;
    private SalaryType salaryType;
    private Integer openings;
    private Instant deadlineTime;
    private JobStatus status;
}
