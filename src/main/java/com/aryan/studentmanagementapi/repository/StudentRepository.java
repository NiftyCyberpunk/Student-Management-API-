package com.aryan.studentmanagementapi.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aryan.studentmanagementapi.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

    @Query("""
        SELECT s
        FROM Student s
        JOIN FETCH s.branch
    """)
    public List<Student> findAllWithBranch();

    @EntityGraph(attributePaths = {"branch"})
    Page<Student> findAll(Specification<Student> spec, Pageable pageable);

    public Optional<Student> findByEmail(String email);
}
