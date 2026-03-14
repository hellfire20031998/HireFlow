package com.hellFire.JobService.dtos.requests;

import com.hellFire.JobService.models.enums.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateJobRequest {
    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Job description is required")
    private String description;

    @NotBlank(message = "About role is required")
    private String aboutRole;

    @NotNull(message = "Department is required")
    private Department department;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Location type is required")
    private LocationType locationType;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    @NotNull(message = "Employment type is required")
    private EmploymentType employmentType;

    @NotNull(message = "Seniority level is required")
    private SeniorityLevel seniorityLevel;

    @NotNull(message = "Industry is required")
    private Industry industry;

    @NotNull(message = "Minimum experience is required")
    @Min(value = 0, message = "Minimum experience cannot be negative")
    private Integer minExperience;

    @NotNull(message = "Maximum experience is required")
    @Min(value = 0, message = "Maximum experience cannot be negative")
    private Integer maxExperience;

    @NotNull(message = "Minimum salary is required")
    @Min(value = 0, message = "Minimum salary cannot be negative")
    private Integer minSalary;

    @NotNull(message = "Maximum salary is required")
    @Min(value = 0, message = "Maximum salary cannot be negative")
    private Integer maxSalary;

    @NotNull(message = "Openings count is required")
    @Min(value = 1, message = "At least 1 opening is required")
    private Integer openings;

    @NotNull(message = "Deadline is required")
    @FutureOrPresent(message = "Deadline must be today or in the future")
    private LocalDate deadline;

    @NotNull(message = "Job status is required")
    private JobStatus status;
}
