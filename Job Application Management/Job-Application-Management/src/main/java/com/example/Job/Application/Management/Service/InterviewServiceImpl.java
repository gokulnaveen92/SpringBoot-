package com.example.Job.Application.Management.Service;

import com.example.Job.Application.Management.Entity.Interview;
import com.example.Job.Application.Management.Repository.InterviewRepo;
import com.example.Job.Application.Management.Repository.JobRepository;
import com.example.Job.Application.Management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class InterviewServiceImpl implements InterviewService{

    @Autowired
    private InterviewRepo interviewRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Interview scheduleInterview(Long jobId, Long userId, LocalDate date, LocalTime time) {
        Interview interview = new Interview();
        interview.setJob(jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found")));
        interview.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        interview.setDate(date);
        interview.setTime(time);
        interview.setStatus("Interview scheduled successfully.");
        return interviewRepository.save(interview);
    }

    @Override
    public String rescheduleInterview(Long interviewId, LocalDate newDate, LocalTime newTime) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interview.setDate(newDate);
        interview.setTime(newTime);
        interview.setStatus("Interview rescheduled successfully.");
        interviewRepository.save(interview);
        return interview.getStatus();
    }

    @Override
    public String cancelInterview(Long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
        interviewRepository.delete(interview);
        return "Interview canceled successfully.";
    }

    @Override
    public Interview updateInterviewStatus(Long interviewId, String status) throws RuntimeException {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
        System.out.println("Status" + status);
        if(status.equals("Positive") || status.equals("Negative")){
            System.out.println("Inside for loop matching");
            interview.setStatus(status);
            return interviewRepository.save(interview);
        }else {
            System.out.println("Not matching");
            throw new RuntimeException("Invalid status value. Allowed values are 'Positive' or 'Negative'");
        }
    }
}
