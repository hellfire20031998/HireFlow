package com.hellFire.JobService.repositories;

import com.hellFire.JobService.models.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobSkillRepository extends JpaRepository<JobSkill, Integer> {
    List<JobSkill> findByJob_IdAndDeleted(Long jobId, boolean deleted);

    List<JobSkill> findByJobIdIn(List<Long> jobIds);
}
