package com.hellFire.JobService.dtos.requests;

import lombok.Data;

import java.util.List;

@Data
public class SelectedSkillsRequest {
    public List<Long> skillIds;
    public Long jobId;
}
