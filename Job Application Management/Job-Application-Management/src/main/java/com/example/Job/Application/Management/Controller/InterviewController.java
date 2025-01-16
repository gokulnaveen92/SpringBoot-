package com.example.Job.Application.Management.Controller;

import com.example.Job.Application.Management.Entity.Interview;
import com.example.Job.Application.Management.Service.InterviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    @Autowired
    private InterviewServiceImpl interviewService;

    @PostMapping("/schedule")
    public ResponseEntity<Map<String,?>> scheduleInterview(@RequestBody Map<String, Object> request) {
        Long jobId = Long.valueOf(request.get("jobId").toString());
        Long userId = Long.valueOf(request.get("userId").toString());
        LocalDate date = LocalDate.parse(request.get("date").toString());
        LocalTime time = LocalTime.parse(request.get("time").toString(), DateTimeFormatter.ofPattern("HH:mm"));
        Interview interview = interviewService.scheduleInterview(jobId, userId, date, time);
        Map<String, Object> response = new HashMap<>();
        response.put("message", interview.getStatus());
        response.put("InterviewId", interview.getInterviewId());

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{interviewId}/reschedule")
    public ResponseEntity<Map<String,String>> rescheduleInterview(
            @PathVariable Long interviewId,
            @RequestBody Map<String, String> requestBody) {
        try{
            LocalDate newDate = LocalDate.parse(requestBody.get("newDate"));
            LocalTime newTime = LocalTime.parse(requestBody.get("newTime"));
            String message = interviewService.rescheduleInterview(interviewId, newDate, newTime);
            return ResponseEntity.ok(Map.of("message", message));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", e.getMessage()
            ));
        }

    }

    @DeleteMapping("/{interviewId}")
    public ResponseEntity<Map<String,String>> cancelInterview(@PathVariable Long interviewId) {
        try {
            String message = interviewService.cancelInterview(interviewId);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
    @PutMapping("/{interviewId}/status")
    public ResponseEntity<Map<String,?>> updateInterviewStatus(
            @PathVariable Long interviewId,
            @RequestBody Interview interview) {
        try {
            System.out.println("status : " + interview.getStatus() );
            Interview updatedinterview = interviewService.updateInterviewStatus(interviewId, interview.getStatus());
//            return ResponseEntity.ok(Map.of("InterviewId",interview.getInterviewId(),
//                    "status",interview.getStatus(),
//                    "message","Interview status updated successfully."));
            Map<String, Object> response = new HashMap<>();
            response.put("InterviewId", updatedinterview.getInterviewId());
            response.put("status", updatedinterview.getStatus());
            response.put("message", "Interview status updated successfully.");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

}
