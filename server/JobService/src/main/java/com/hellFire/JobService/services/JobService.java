package com.hellFire.JobService.services;

import com.hellFire.JobService.dtos.JobDto;
import com.hellFire.JobService.dtos.requests.CreateJobRequest;
import com.hellFire.JobService.dtos.requests.CreateJobSkillRequest;
import com.hellFire.JobService.dtos.responses.JobResponse;
import com.hellFire.JobService.exceptions.ErrorCode;
import com.hellFire.JobService.exceptions.JobNotFoundException;
import com.hellFire.JobService.mappers.IJobMapper;
import com.hellFire.JobService.mappers.ISkillMapper;
import com.hellFire.JobService.models.Job;
import com.hellFire.JobService.models.Skill;
import com.hellFire.JobService.repositories.IJobRepository;
import com.hellFire.JobService.security.UserContext;
import com.hellFire.JobService.security.UserContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        Long userId = ctx.getUserId();
        Long tenantId = ctx.getTenantId();
        String username = ctx.getUsername();
        Job job = toEntity(createJobRequest);
        job.setCreatedByName(username);
        job.setCreatedBy(userId);
        job.setTenantId(tenantId);
        job = jobRepository.save(job);
        return toJobDto(job);
    }

    public List<Skill> createSkills(Job job, CreateJobSkillRequest request){
        return jobSkillService.createJobSkill(job, request);
    }

    @Transactional(readOnly = true)
    public JobResponse getJobByID(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job Not found for id "+ id));
        List<Skill> skillList = jobSkillService.getAllSkillByJobId(job.getId());
        return new JobResponse(toJobDto(job), skillService.toDtoList(skillList));
    }

    @Transactional(readOnly = true)
    public List<JobResponse> getAllJobs() {

        List<Job> jobList = jobRepository.findByDeleted(false);

        List<Long> jobIds = jobList.stream()
                .map(Job::getId)
                .toList();

        Map<Long, List<Skill>> jobSkillMap =
                jobSkillService.getSkillsForJobIds(jobIds);

        List<JobResponse> response = new ArrayList<>();

        for (Job job : jobList) {
            List<Skill> skills = jobSkillMap.getOrDefault(job.getId(), List.of());

            response.add(
                    new JobResponse(
                            toJobDto(job),
                            skillService.toDtoList(skills)
                    )
            );
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
