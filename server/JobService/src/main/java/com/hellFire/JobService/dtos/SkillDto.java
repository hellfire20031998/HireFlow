package com.hellFire.JobService.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SkillDto extends BaseEntityDto {
    private String name;

}
