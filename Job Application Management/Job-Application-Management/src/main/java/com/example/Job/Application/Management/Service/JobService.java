package com.example.Job.Application.Management.Service;

import com.example.Job.Application.Management.Entity.Job;
import com.example.Job.Application.Management.Entity.User;

import java.util.List;

public interface JobService {

    public Job createJob(Job job);
    public List<Job> filterBySkillAndLocation(String skill, String location);
    public List<Job> filterBySkillAndExperience(String skill, String experience);
    public String applyForJob(Long jobId, User user);
}
