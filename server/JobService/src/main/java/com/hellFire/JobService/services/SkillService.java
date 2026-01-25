package com.hellFire.JobService.services;

import com.hellFire.JobService.dtos.SkillDto;
import com.hellFire.JobService.dtos.requests.CreateSkillRequest;
import com.hellFire.JobService.dtos.responses.ApiResponse;
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

    public SkillDto createSkill(CreateSkillRequest request) {
        Skill skill = toEntity(request);
        skill = skillRepository.save(skill);
        return toDto(skill);
    }

    public List<SkillDto> getAllSkills() {
        return toDtoList(skillRepository.findAll());
    }

    public Skill getSkillById(Long skillId) {
        return skillRepository.findById(skillId).orElseThrow(()-> new SkillNotFoundException("Skill not found for id: " + skillId));
    }

    public SkillDto toDto(Skill skill) {
        return skillMapper.toDto(skill);
    }

    public List<SkillDto> toDtoList(List<Skill> skills) {
        return skillMapper.toDtoList(skills);
    }

    public Skill toEntity(CreateSkillRequest request) {
       return skillMapper.toEntity(request);
    }
}
