package com.hellFire.JobService.repositories;

import com.hellFire.JobService.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findTop10ByNameContainingIgnoreCase(String searchTerm);
    List<Skill> findTop10ByOrderByCreatedAtDesc();
}
