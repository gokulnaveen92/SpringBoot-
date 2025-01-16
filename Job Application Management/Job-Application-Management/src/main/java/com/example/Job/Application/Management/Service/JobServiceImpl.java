package com.example.Job.Application.Management.Service;

import com.example.Job.Application.Management.Entity.Job;
import com.example.Job.Application.Management.Entity.User;
import com.example.Job.Application.Management.Repository.JobRepository;
import com.example.Job.Application.Management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public List<Job> filterBySkillAndLocation(String skill, String location) {
        return jobRepository.findBySkillsRequiredContainingAndLocation(skill , location);
    }

    @Override
    public List<Job> filterBySkillAndExperience(String skill, String experience) {
        return jobRepository.findBySkillsRequiredContainingAndExperienceLevel( skill , experience);
    }

    @Override
    public String applyForJob(Long jobId, User user) {
        if (jobRepository.existsById(jobId)) {
            Job job = jobRepository.findById(jobId).get();
            User existUser = userRepository.findByUserId(user.getUserId());
            existUser.setResume(user.getResume());
            existUser.setCoverLetter(user.getCoverLetter());
//            existUser.addJob(job);
            job.addUser(existUser);
            jobRepository.save(job);
            userRepository.save(existUser);
            return "\"message\" : \"Application submitted successfully.\"";
        }
        return "\"error\" : \"No such job exists\"";
    }
}
