package com.example.Job.Application.Management.Repository;

import com.example.Job.Application.Management.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    public Job findByJobId(Long jobId);
    public List<Job> findBySkillsRequiredContainingAndLocation(String skill, String location);
    public List<Job> findBySkillsRequiredContainingAndExperienceLevel(String skill, String experienceLevel);
}
