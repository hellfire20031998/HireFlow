package com.hellFire.JobService.mappers;

import com.hellFire.JobService.dtos.SkillDto;
import com.hellFire.JobService.dtos.requests.CreateSkillRequest;
import com.hellFire.JobService.models.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ISkillMapper {

    SkillDto toDto(Skill skill);
    List<SkillDto> toDtoList(List<Skill> skills);
    Skill toEntity(CreateSkillRequest request);
}
