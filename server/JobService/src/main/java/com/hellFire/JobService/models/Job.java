package com.hellFire.JobService.models;

import com.hellFire.JobService.models.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    private Industry industry;

    private Integer minExperience;
    private Integer maxExperience;

    private Integer minSalary;
    private Integer maxSalary;

    private Integer openings;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private Long createdBy;

    private Long tenantId;
}
