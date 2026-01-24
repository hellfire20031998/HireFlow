package com.hellFire.JobService.dtos.requests;


import lombok.Data;

import java.util.List;

@Data
public class CreateJobSkillRequest {

    private Long jobId;

    private List<JobSkillItem> skills;

    @Data
    public static class JobSkillItem {
        private Long skillId;
        private Integer minExperienceYears;
        private Integer maxExperienceYears;
        private Integer priority;
    }
}
