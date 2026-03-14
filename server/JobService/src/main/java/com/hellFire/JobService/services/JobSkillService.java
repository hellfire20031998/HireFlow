package com.hellFire.JobService.services;

import com.hellFire.JobService.dtos.requests.CreateJobSkillRequest;
import com.hellFire.JobService.models.Job;
import com.hellFire.JobService.models.JobSkill;
import com.hellFire.JobService.models.Skill;
import com.hellFire.JobService.repositories.IJobSkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobSkillService {

    private final IJobSkillRepository jobSkillRepository;
    private final SkillService skillService;

    public JobSkillService(IJobSkillRepository jobSkillRepository, SkillService skillService) {
        this.jobSkillRepository = jobSkillRepository;
        this.skillService = skillService;
    }

    public List<Skill> getAllSkillByJobId(Long jobId) {
        List<JobSkill> jobSkillList = jobSkillRepository.findByJob_IdAndDeleted(jobId, false);
        return jobSkillList.stream().map(JobSkill::getSkill).collect(Collectors.toList());
    }

    @Transactional
    public List<Skill> createJobSkill(Job job, CreateJobSkillRequest request) {

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

        jobSkillList = jobSkillRepository.saveAll(jobSkillList);

        return jobSkillList.stream().map(JobSkill::getSkill).toList();
    }

    public Map<Long, List<Skill>> getSkillsForJobIds(List<Long> jobIds) {
        List<JobSkill> jobSkills = jobSkillRepository.findByJobIdIn(jobIds);

        Map<Long, List<Skill>> map = new HashMap<>();

        for (JobSkill js : jobSkills) {
            map.computeIfAbsent(js.getJob().getId(), k -> new ArrayList<>())
                    .add(js.getSkill());
        }

        return map;
    }


}
