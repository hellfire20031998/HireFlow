package com.hellFire.JobService.services;

import com.hellFire.JobService.dtos.requests.CreateJobRequest;
import com.hellFire.JobService.exceptions.ErrorCode;
import com.hellFire.JobService.exceptions.JobNotFoundException;
import com.hellFire.JobService.mappers.IJobMapper;
import com.hellFire.JobService.models.Job;
import com.hellFire.JobService.repositories.IJobRepository;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final IJobRepository jobRepository;
    private final IJobMapper jobMapper;

    public JobService(IJobRepository jobRepository, IJobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    public Job createJob(CreateJobRequest createJobRequest, Long createdBy, Long tenantId) {
        Job job = jobMapper.toEntity(createJobRequest);
        job.setCreatedBy(createdBy);
        job.setTenantId(tenantId);
        job = jobRepository.save(job);
        return job;
    }

    public Job getJob(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException(ErrorCode.JOB_NOT_FOUND, "Job Not found for id "+ id));
    }

}
