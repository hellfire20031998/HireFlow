package com.hellFire.JobService.dtos;

import com.hellFire.JobService.models.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class JobDto extends BaseEntityDto {
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
    private Long createdBy;
    private String createdByName;
    private Long tenantId;
    private String tenantName;
    private Long lastUpdatedBy;
    private String lastUpdatedByName;
}
