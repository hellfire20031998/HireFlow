package com.hellFire.JobService.services;

import com.hellFire.JobService.dtos.JobDto;
import com.hellFire.JobService.dtos.requests.CreateJobRequest;
import com.hellFire.JobService.dtos.requests.CreateJobSkillRequest;
import com.hellFire.JobService.dtos.requests.UpdateJobRequest;
import com.hellFire.JobService.dtos.responses.JobResponse;
import com.hellFire.JobService.exceptions.ErrorCode;
import com.hellFire.JobService.exceptions.AccessDeniedException;
import com.hellFire.JobService.exceptions.JobNotFoundException;
import com.hellFire.JobService.mappers.IJobMapper;
import com.hellFire.JobService.mappers.ISkillMapper;
import com.hellFire.JobService.models.Job;
import com.hellFire.JobService.models.JobSkill;
import com.hellFire.JobService.models.Skill;
import com.hellFire.JobService.models.enums.JobStatus;
import com.hellFire.JobService.models.enums.UserType;
import com.hellFire.JobService.repositories.IJobRepository;
import com.hellFire.JobService.security.UserContext;
import com.hellFire.JobService.security.UserContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    private final IJobRepository jobRepository;
    private final IJobMapper jobMapper;
    private final JobSkillService jobSkillService;
    private final SkillService skillService;

    public JobService(IJobRepository jobRepository, IJobMapper jobMapper, JobSkillService jobSkillService, ISkillMapper skillMapper, SkillService skillService) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.jobSkillService = jobSkillService;
        this.skillService = skillService;
    }

    @Transactional
    public JobDto createJob(CreateJobRequest createJobRequest) {
        UserContext ctx = UserContextHolder.get();
        if (ctx == null) {
            throw new IllegalStateException("User context not set");
        }
        Long userId = ctx.getUserId();
        Long tenantId = ctx.getTenantId();
        String username = ctx.getUsername();
        String tenantName = ctx.getTenantName();
        Job job = toEntity(createJobRequest);
        if (job.getIndustries() == null) {
            job.setIndustries(new HashSet<>());
        }
        if (job.getStatus() == null) {
            job.setStatus(JobStatus.DRAFT);
        }
        job.setTenantName(tenantName);

        job.setCreatedByName(username);
        job.setCreatedBy(userId);
        job.setTenantId(tenantId);
        job = jobRepository.save(job);

        // Optionally attach skills during creation
        if (createJobRequest.getSkills() != null && !createJobRequest.getSkills().isEmpty()) {
            jobSkillService.replaceJobSkills(job, createJobRequest.getSkills());
        }

        return toJobDto(job);
    }

    public List<Skill> createSkills(Job job, CreateJobSkillRequest request){
        return jobSkillService.createJobSkill(job, request);
    }

    @Transactional
    public JobDto updateJob(Long id, UpdateJobRequest request) {
        UserContext ctx = UserContextHolder.get();
        if (ctx == null) {
            throw new IllegalStateException("User context not set");
        }
        Job job = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job not found for id " + id));
        jobMapper.updateEntityFromRequest(request, job);
        job.setLastUpdatedBy(ctx.getUserId());
        job.setLastUpdatedByName(ctx.getUsername());
        job = jobRepository.save(job);

        // If skills is present: replace existing mappings with the new list
        if (request.getSkills() != null) {
            jobSkillService.replaceJobSkills(job, request.getSkills());
        }

        return toJobDto(job);
    }

    @Transactional(readOnly = true)
    public JobResponse getJobByID(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job Not found for id "+ id));
        List<JobSkill> jobSkills = jobSkillService.getAllSkillByJobId(job.getId());
        return new JobResponse(toJobDto(job), skillService.toDtoListFromJobSkills(jobSkills));
    }

    @Transactional(readOnly = true)
    public List<JobResponse> getAllJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        Page<Job> jobPage = jobRepository.findByDeleted(false, pageable);
        return toJobResponseList(jobPage.getContent());
    }

    @Transactional(readOnly = true)
    public List<JobResponse> getAllJobsByCreatedBy(Long createdBy) {
        return toJobResponseList(jobRepository.findByCreatedByAndDeleted(createdBy, false));
    }

    @Transactional(readOnly = true)
    public List<JobResponse> getAllJobsByTenantId(Long tenantId) {
        return toJobResponseList(jobRepository.findByTenantIdAndDeleted(tenantId, false));
    }

    private List<JobResponse> toJobResponseList(List<Job> jobList) {
        List<Long> jobIds = jobList.stream()
                .map(Job::getId)
                .toList();
        Map<Long, List<JobSkill>> jobSkillMap = jobSkillService.getSkillsForJobIds(jobIds);
        List<JobResponse> response = new ArrayList<>();
        for (Job job : jobList) {
            List<JobSkill> jobSkills = jobSkillMap.getOrDefault(job.getId(), List.of());
            response.add(new JobResponse(toJobDto(job), skillService.toDtoListFromJobSkills(jobSkills)));
        }
        return response;
    }

    public JobDto toJobDto(Job job) {
        return jobMapper.toDto(job);
    }
    public List<JobDto> toJobDtoList(List<Job> jobList) {
        return jobMapper.toDtoList(jobList);
    }

    public Job toEntity(CreateJobRequest createJobRequest) {
        return jobMapper.toEntity(createJobRequest);
    }

}
