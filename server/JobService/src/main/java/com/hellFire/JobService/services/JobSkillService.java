package com.hellFire.JobService.services;

import com.hellFire.JobService.dtos.requests.CreateJobSkillRequest;
import com.hellFire.JobService.dtos.requests.SelectedSkillsRequest;
import com.hellFire.JobService.models.Job;
import com.hellFire.JobService.models.JobSkill;
import com.hellFire.JobService.models.Skill;
import com.hellFire.JobService.repositories.IJobSkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobSkillService {

    private final IJobSkillRepository jobSkillRepository;
    private final JobService jobService;
    private final SkillService skillService;

    public JobSkillService(IJobSkillRepository jobSkillRepository, JobService jobService, SkillService skillService) {
        this.jobSkillRepository = jobSkillRepository;
        this.jobService = jobService;
        this.skillService = skillService;
    }

    @Transactional
    public void createJobSkill(CreateJobSkillRequest request) {

        Job job = jobService.getJob(request.getJobId());
        List<JobSkill> jobSkillList = new ArrayList<>();

        for (CreateJobSkillRequest.JobSkillItem item : request.getSkills()) {

            Skill skill = skillService.getSkillById(item.getSkillId());

            JobSkill jobSkill = new JobSkill();
            jobSkill.setJob(job);
            jobSkill.setSkill(skill);
            jobSkill.setMinExperienceYears(item.getMinExperienceYears());
            jobSkill.setMaxExperienceYears(item.getMaxExperienceYears());
            jobSkill.setPriority(item.getPriority());

            jobSkillList.add(jobSkill);
        }

        jobSkillRepository.saveAll(jobSkillList);
    }

}
