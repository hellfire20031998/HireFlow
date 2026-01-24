package com.hellFire.JobService.repositories;

import com.hellFire.JobService.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobRepository extends JpaRepository<Job, Long> {
}
