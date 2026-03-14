package com.hellFire.JobService.dtos.responses;

import com.hellFire.JobService.dtos.JobDto;
import com.hellFire.JobService.dtos.SkillDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {

    private JobDto job;
    private List<SkillDto> skills;

}
