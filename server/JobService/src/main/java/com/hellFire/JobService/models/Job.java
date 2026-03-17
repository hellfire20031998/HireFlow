package com.hellFire.JobService.models;

import com.hellFire.JobService.models.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(length = 5000)
    private String aboutRole;

    @Enumerated(EnumType.STRING)
    private Department department;

    private String location;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    private SeniorityLevel seniorityLevel;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = Industry.class)
    @CollectionTable(name = "job_industries", joinColumns = @JoinColumn(name = "job_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "industry")
    @Builder.Default
    private Set<Industry> industries = new HashSet<>();

    private Integer minExperience;
    private Integer maxExperience;

    private Integer minSalary;
    private Integer maxSalary;

    @Enumerated(EnumType.STRING)
    private SalaryCurrency salaryCurrency;

    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    private Integer openings;

    private Instant deadlineTime;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private Long createdBy;

    private String createdByName;

    private Long tenantId;

    private Long lastUpdatedBy;
    private String lastUpdatedByName;
}
