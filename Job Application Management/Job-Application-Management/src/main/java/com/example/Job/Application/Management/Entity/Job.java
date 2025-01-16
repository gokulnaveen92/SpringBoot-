package com.example.Job.Application.Management.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    @NotBlank(message = "The 'title' is required")
    private String title;
    @NotBlank(message = "The 'description' is required")
    private String description;
    @NotBlank(message = "The 'location' is required")
    private String location;

    @ElementCollection
    @NotEmpty(message = "At least one skill is required")
    private List<String> skillsRequired;

    @NotBlank(message = "The 'experienceLevel' is required")
    private String experienceLevel;
    @NotBlank(message = "The 'salaryRange' is required")
    private String salaryRange;
    @NotBlank(message = "The 'companyName' is required")
    private String companyName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_job",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    List<User> userList = new ArrayList<>();

    public void addUser(User user){
        this.userList.add(user);
        user.getJobList().add(this);
    }

    public void removeUser(User user){
        this.userList.remove(user);
        user.getJobList().remove(this);
    }

}
