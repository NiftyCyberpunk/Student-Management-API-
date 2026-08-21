package com.aryan.studentmanagementapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.dto.StudentRequestStatusUpdateDTO;
import com.aryan.studentmanagementapi.dto.StudentUpdateRequestDTO;
import com.aryan.studentmanagementapi.dto.StudentUpdateRequestResponseDTO;
import com.aryan.studentmanagementapi.exception.BadRequestException;
import com.aryan.studentmanagementapi.exception.BranchNotFoundException;
import com.aryan.studentmanagementapi.exception.StudentAlreadyExistsException;
import com.aryan.studentmanagementapi.exception.StudentNotFoundException;
import com.aryan.studentmanagementapi.exception.UpdateRequestNotFoundException;
import com.aryan.studentmanagementapi.exception.UserNotFoundException;
import com.aryan.studentmanagementapi.mapper.StudentMapper;
import com.aryan.studentmanagementapi.model.Branch;
import com.aryan.studentmanagementapi.model.Role;
import com.aryan.studentmanagementapi.model.Student;
import com.aryan.studentmanagementapi.model.StudentUpdateRequest;
import com.aryan.studentmanagementapi.model.UpdateRequestStatus;
import com.aryan.studentmanagementapi.model.User;
import com.aryan.studentmanagementapi.repository.BranchRepository;
import com.aryan.studentmanagementapi.repository.StudentRepository;
import com.aryan.studentmanagementapi.repository.StudentUpdateRequestRepository;
import com.aryan.studentmanagementapi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentUpdateRequestService {
    
    private final StudentUpdateRequestRepository studentUpdateRequestRepository;
    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final StudentMapper mapper;

    public StudentUpdateRequestService(StudentUpdateRequestRepository studentUpdateRequestRepository, StudentRepository studentRepository, BranchRepository branchRepository, UserRepository userRepository, StudentMapper mapper) {
        this.studentUpdateRequestRepository = studentUpdateRequestRepository;
        this.studentRepository = studentRepository;
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public void createRequest(Integer registrationNo, String username, StudentUpdateRequestDTO studentUpdateRequest) {
        
        if(studentUpdateRequest.getName() == null && studentUpdateRequest.getEmail() == null && studentUpdateRequest.getBranch() == null && studentUpdateRequest.getYear() == null){
            throw new BadRequestException("Empty update request.");
        }

        Student student = studentRepository
            .findById(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));

        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException());
        
        if(user.getRole() == Role.STUDENT){
            if(!user.getStudent().getRegistrationNo().equals(registrationNo)){
                throw new AccessDeniedException("You dont have permission to update this information.");
            }
        }

        Branch branch = null;

        if(studentUpdateRequest.getBranch() != null) {
            branch = branchRepository
                .findByName(studentUpdateRequest.getBranch())
                .orElseThrow(() -> new BranchNotFoundException(studentUpdateRequest.getBranch()));
        }

        StudentUpdateRequest updateRequest = new StudentUpdateRequest();

        updateRequest.setStudent(student);
        updateRequest.setRequestedBy(user);
        updateRequest.setRequestedName(studentUpdateRequest.getName());
        updateRequest.setRequestedEmail(studentUpdateRequest.getEmail());
        updateRequest.setRequestedBranch(branch);
        updateRequest.setRequestedYear(studentUpdateRequest.getYear());

        studentUpdateRequestRepository.save(updateRequest);
    }

    public List<StudentUpdateRequestResponseDTO> getPendingRequests() {

        List<StudentUpdateRequest> requests = studentUpdateRequestRepository.findByStatus(UpdateRequestStatus.PENDING);

        return requests.stream()
            .map(mapper::toStudentUpdateRequestResponse)
            .toList();
    }

    @Transactional
    public void updateRequestStatus(Long requestId, String username , StudentRequestStatusUpdateDTO statusUpdate) {

        StudentUpdateRequest request = studentUpdateRequestRepository
            .findById(requestId)
            .orElseThrow(() -> new UpdateRequestNotFoundException());

        User processedBy = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException());



        if(request.getStatus() != UpdateRequestStatus.PENDING) {
            throw new BadRequestException("Update request is already processed.");
        }

        if(statusUpdate.getStatus() == UpdateRequestStatus.REJECTED) {
            request.setStatus(UpdateRequestStatus.REJECTED);
            request.setProcessedBy(processedBy);
            request.setProcessedAt(LocalDateTime.now());
            return;
        }

        if(statusUpdate.getStatus() == UpdateRequestStatus.APPROVED) {

            Student student = request.getStudent();

            if(request.getRequestedName() != null) {
                student.setName(request.getRequestedName());
            }

            if(request.getRequestedEmail() != null) {
                studentRepository
                    .findByEmail(request.getRequestedEmail())
                    .ifPresent(foundStudent -> {
                        if(!foundStudent.getRegistrationNo().equals(student.getRegistrationNo())){
                            throw new StudentAlreadyExistsException(
                                "Student with email " + request.getRequestedEmail() + " already exists."
                            );
                        }
                    }
                );
                student.setEmail(request.getRequestedEmail());
            }

            if(request.getRequestedBranch() != null) {
                student.setBranch(request.getRequestedBranch());
            }

            if(request.getRequestedYear() != null) {
                student.setYear(request.getRequestedYear());
            }

            request.setStatus(UpdateRequestStatus.APPROVED);
            request.setProcessedBy(processedBy);
            request.setProcessedAt(LocalDateTime.now());

            return;
        }
        throw new BadRequestException("Invalid Request status");
    }
}
