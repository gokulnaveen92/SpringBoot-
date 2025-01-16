package com.example.Job.Application.Management.Entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String resume;
    private String coverLetter;
    private String role;
    @ElementCollection
    private List<String> skills;

    @ManyToMany(mappedBy = "userList" , cascade = CascadeType.ALL)
    List<Job> jobList = new ArrayList<>();

    public void addJob(Job job){
        this.jobList.add(job);
        job.getUserList().add(this);
    }

    public void removeJob(Job job){
        this.jobList.remove(job);
        job.getUserList().remove(this);
    }

}
