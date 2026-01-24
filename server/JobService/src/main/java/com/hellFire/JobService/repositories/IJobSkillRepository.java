package com.hellFire.JobService.repositories;

import com.hellFire.JobService.models.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobSkillRepository extends JpaRepository<JobSkill, Integer> {
}
