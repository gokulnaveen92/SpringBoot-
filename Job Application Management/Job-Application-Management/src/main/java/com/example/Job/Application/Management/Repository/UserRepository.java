package com.example.Job.Application.Management.Repository;

import com.example.Job.Application.Management.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserId(Long userId);
    public Optional<User> findByUsername(String username);

}
