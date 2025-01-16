package com.example.Job.Application.Management.Controller;

import com.example.Job.Application.Management.Entity.Job;
import com.example.Job.Application.Management.Entity.User;
import com.example.Job.Application.Management.Service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Job> addJob(@Valid @RequestBody Job job) {
        Job savedJob = jobService.createJob(job);
        return ResponseEntity.ok(savedJob);
    }

    @GetMapping(value = "/search",params = {"skill","location"})
    public ResponseEntity<?> searchJob(@RequestParam String skill, @RequestParam String location) {
        List<Job> matchedJobs = jobService.filterBySkillAndLocation(skill, location);
        if (matchedJobs.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No matching jobs found"));
        else
            return ResponseEntity.ok(matchedJobs);
    }

    @GetMapping(value = "/search",params = {"skill","experienceLevel"})
    public ResponseEntity<?> searchJobsBySkillandExp(@RequestParam String skill, @RequestParam String experienceLevel) {
        List<Job> matchedJobs = jobService.filterBySkillAndExperience(skill, experienceLevel);
        if(matchedJobs.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No matching jobs found"));
        else
            return ResponseEntity.ok(matchedJobs);
    }

    @PostMapping("/{jobId}/apply")
    public  ResponseEntity<String> applyJob(@RequestBody User user, @PathVariable Long jobId){
        String status = jobService.applyForJob(jobId, user);
        if(status.equals("message : 'Application submitted successfully.'"))
            return ResponseEntity.ok(status);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
    }
}
