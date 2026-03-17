package com.hellFire.JobService.services;

import com.hellFire.JobService.dtos.requests.CreateJobSkillRequest;
import com.hellFire.JobService.dtos.requests.CreateJobSkillRequest.JobSkillItem;
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

    public List<JobSkill> getAllSkillByJobId(Long jobId) {
        List<JobSkill> jobSkillList = jobSkillRepository.findByJob_IdAndDeleted(jobId, false);
        return jobSkillList;
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

    /**
     * Replace all skills for a job with the provided items.
     * - If items is null: do nothing (leave existing mappings as-is).
     * - If items is empty: remove all existing skills for the job.
     */
    @Transactional
    public void replaceJobSkills(Job job, List<JobSkillItem> items) {
        if (items == null) {
            return;
        }
        // remove all existing mappings
        jobSkillRepository.deleteByJob_Id(job.getId());

        if (items.isEmpty()) {
            return; // user explicitly wants no skills
        }

        CreateJobSkillRequest request = new CreateJobSkillRequest();
        request.setJobId(job.getId());
        request.setSkills(items);
        createJobSkill(job, request);
    }

    public Map<Long, List<JobSkill>> getSkillsForJobIds(List<Long> jobIds) {
        List<JobSkill> jobSkills = jobSkillRepository.findByJobIdIn(jobIds);

        Map<Long, List<JobSkill>> map = new HashMap<>();

        for (JobSkill js : jobSkills) {
            map.computeIfAbsent(js.getJob().getId(), k -> new ArrayList<>())
                    .add(js);
        }

        return map;
    }


}
