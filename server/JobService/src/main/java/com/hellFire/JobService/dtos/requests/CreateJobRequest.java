package com.hellFire.JobService.dtos.requests;

import com.hellFire.JobService.dtos.requests.CreateJobSkillRequest.JobSkillItem;
import com.hellFire.JobService.models.enums.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
public class CreateJobRequest {
    @NotBlank
    private String title;

    @NotBlank
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

    // Optional: full job-skill mapping for this job
    private List<@Valid JobSkillItem> skills;
}
