package com.hellFire.JobService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "job_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSkill extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private Integer minExperienceYears;
    private Integer maxExperienceYears;

    private Integer priority;
}

