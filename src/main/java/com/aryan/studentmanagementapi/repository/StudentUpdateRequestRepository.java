package com.aryan.studentmanagementapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aryan.studentmanagementapi.model.StudentUpdateRequest;
import com.aryan.studentmanagementapi.model.UpdateRequestStatus;

@Repository
public interface StudentUpdateRequestRepository extends JpaRepository<StudentUpdateRequest, Long> {
    
    List<StudentUpdateRequest> findByStatus(UpdateRequestStatus status);
}
