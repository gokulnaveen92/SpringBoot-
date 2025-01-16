package com.example.Job.Application.Management.Repository;

import com.example.Job.Application.Management.Entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepo extends JpaRepository<Interview , Long> {
}
