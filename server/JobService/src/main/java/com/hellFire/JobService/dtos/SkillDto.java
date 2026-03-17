package com.hellFire.JobService.dtos;

import com.hellFire.JobService.models.enums.SkillPriority;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SkillDto extends BaseEntityDto {
    private String name;

    // Per-job mapping details (from JobSkill)
    private Integer minExperienceYears;
    private Integer maxExperienceYears;
    private SkillPriority priority;
}
