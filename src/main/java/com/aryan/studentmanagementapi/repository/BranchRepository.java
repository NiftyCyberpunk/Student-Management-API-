package com.aryan.studentmanagementapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aryan.studentmanagementapi.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

    public Optional<Branch> findByName(String name);
    
}
