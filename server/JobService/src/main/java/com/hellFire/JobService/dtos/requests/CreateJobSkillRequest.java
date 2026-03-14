package com.hellFire.JobService.dtos.requests;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


@Data
public class CreateJobSkillRequest {

    @NotNull(message = "Job ID is required")
    private Long jobId;

    @NotEmpty(message = "Skills list cannot be empty")
    private List<@Valid JobSkillItem> skills;

    @Data
    public static class JobSkillItem {

        @NotNull(message = "Skill ID is required")
        private Long skillId;

        @NotNull(message = "Minimum experience is required")
        @Min(value = 0, message = "Minimum experience cannot be negative")
        private Integer minExperienceYears;

        @NotNull(message = "Maximum experience is required")
        @Min(value = 0, message = "Maximum experience cannot be negative")
        private Integer maxExperienceYears;

        @NotNull(message = "Priority is required")
        @Min(value = 1, message = "Priority must be at least 1")
        private Integer priority;
    }
}