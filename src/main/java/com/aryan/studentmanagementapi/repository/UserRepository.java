package com.aryan.studentmanagementapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aryan.studentmanagementapi.model.Role;
import com.aryan.studentmanagementapi.model.Student;
import com.aryan.studentmanagementapi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);

    Optional<User> findByStudent(Student student);

    long countByRole(Role role);
}
