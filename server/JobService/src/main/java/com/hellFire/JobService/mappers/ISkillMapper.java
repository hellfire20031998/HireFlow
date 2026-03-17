package com.hellFire.JobService.mappers;

import com.hellFire.JobService.dtos.SkillDto;
import com.hellFire.JobService.dtos.requests.CreateSkillRequest;
import com.hellFire.JobService.models.JobSkill;
import com.hellFire.JobService.models.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ISkillMapper {

    // Pure skill mapping (no per-job info)
    SkillDto toDto(Skill skill);
    List<SkillDto> toDtoList(List<Skill> skills);

    // Mapping from JobSkill (includes experience and priority)
    @Mapping(source = "skill.name", target = "name")
    SkillDto fromJobSkill(JobSkill jobSkill);
    List<SkillDto> fromJobSkills(List<JobSkill> jobSkills);

    Skill toEntity(CreateSkillRequest request);
}
