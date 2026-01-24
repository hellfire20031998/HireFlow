package com.hellFire.JobService.services;

import com.hellFire.JobService.dtos.requests.CreateSkillRequest;
import com.hellFire.JobService.exceptions.ErrorCode;
import com.hellFire.JobService.exceptions.SkillNotFoundException;
import com.hellFire.JobService.mappers.ISkillMapper;
import com.hellFire.JobService.models.Skill;
import com.hellFire.JobService.repositories.ISkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final ISkillRepository skillRepository;
    private final ISkillMapper skillMapper;

    public SkillService(ISkillRepository skillRepository, ISkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    public Skill createSkill(CreateSkillRequest request) {
        Skill skill = skillMapper.toEntity(request);
        skill = skillRepository.save(skill);
        return skill;
    }

    public List<Skill> getAllSkills(List<Long> skillIds) {
        return skillRepository.findAllById(skillIds);
    }

    public Skill getSkillById(Long skillId) {
        return skillRepository.findById(skillId).orElseThrow(()-> new SkillNotFoundException(ErrorCode.SKILL_NOT_FOUND, "Skill not found for id: " + skillId));
    }
}
