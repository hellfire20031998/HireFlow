package com.hellFire.JobService.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSkillRequest {
    @NotBlank(message = "skill name can not be empty")
    private String name;
}
