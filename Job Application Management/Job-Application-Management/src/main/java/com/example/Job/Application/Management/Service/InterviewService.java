package com.example.Job.Application.Management.Service;

import com.example.Job.Application.Management.Entity.Interview;

import java.time.LocalDate;
import java.time.LocalTime;

public interface InterviewService {

    public Interview scheduleInterview(Long jobId, Long userId, LocalDate date, LocalTime time);
    public String rescheduleInterview(Long interviewId, LocalDate newDate, LocalTime newTime);
    public String cancelInterview(Long interviewId);
    public Interview updateInterviewStatus(Long interviewId, String status);

}
