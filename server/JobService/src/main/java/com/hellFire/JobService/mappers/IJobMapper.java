package com.hellFire.JobService.mappers;

import com.hellFire.JobService.dtos.JobDto;
import com.hellFire.JobService.dtos.requests.CreateJobRequest;
import com.hellFire.JobService.models.Job;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IJobMapper {

    JobDto toDto(Job job);
    Job toEntity(CreateJobRequest request);
    List<JobDto> toDtoList(List<Job> jobs);
}
