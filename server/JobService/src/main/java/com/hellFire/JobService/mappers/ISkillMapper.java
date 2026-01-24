package com.hellFire.JobService.mappers;

import com.hellFire.JobService.dtos.SkillDto;
import com.hellFire.JobService.dtos.requests.CreateSkillRequest;
import com.hellFire.JobService.models.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ISkillMapper {

    SkillDto toDto(Skill skill);
    Skill toEntity(CreateSkillRequest request);
}
