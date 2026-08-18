package com.aryan.studentmanagementapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aryan.studentmanagementapi.dto.BranchStudentCountDTO;
import com.aryan.studentmanagementapi.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

    @Query("""
            SELECT new com.aryan.studentmanagementapi.dto.BranchStudentCountDTO(b.name, COUNT(s.registrationNo))
            FROM Branch b
            LEFT JOIN b.students s
            GROUP BY b.id, b.name
        """)
    List<BranchStudentCountDTO> branchStudentCount();

    Optional<Branch> findByName(String name);
    
}
