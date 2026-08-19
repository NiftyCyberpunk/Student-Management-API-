package com.aryan.studentmanagementapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.dto.BranchRequestDTO;
import com.aryan.studentmanagementapi.dto.BranchResponseDTO;
import com.aryan.studentmanagementapi.dto.BranchStudentCountDTO;
import com.aryan.studentmanagementapi.exception.BadRequestException;
import com.aryan.studentmanagementapi.exception.BranchAlreadyExistsException;
import com.aryan.studentmanagementapi.exception.BranchNotFoundException;
import com.aryan.studentmanagementapi.mapper.BranchMapper;
import com.aryan.studentmanagementapi.model.Branch;
import com.aryan.studentmanagementapi.repository.BranchRepository;
import com.aryan.studentmanagementapi.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class BranchService {
    
    private final BranchRepository branchRepository;
    private final StudentRepository studentRepository;
    private final BranchMapper mapper;

    public BranchService(BranchRepository branchRepository, StudentRepository studentRepository, BranchMapper mapper){
        this.branchRepository = branchRepository;
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    public List<Branch> getAllBranchs(){
        return branchRepository.findAll();
    }

    public List<BranchStudentCountDTO> getStudentCount(){
        return branchRepository.branchStudentCount();
    }

    @Transactional
    public BranchResponseDTO addBranch(BranchRequestDTO dto){

        branchRepository
            .findByName(dto.getName())
            .ifPresent(existingBranch -> {
                    throw new BranchAlreadyExistsException(
                        "Branch with name " + dto.getName() + " already exists." 
                    );
                }
            );
        
        Branch branch = mapper.toBranch(dto);
        branchRepository.save(branch);

        return mapper.toBranchResponseDTO(branch);
    }

    @Transactional
    public BranchResponseDTO updateBranch(String name, BranchRequestDTO dto){

        Branch branch = branchRepository
            .findByName(name)
            .orElseThrow(() -> new BranchNotFoundException(name));


        branchRepository
            .findByName(dto.getName())
            .ifPresent(existingBranch -> {
                    if(!existingBranch.getId().equals(branch.getId())){
                        throw new BranchAlreadyExistsException(
                            "Branch with name " + dto.getName() + " already exists." 
                        );
                    }
                }
            );
        
        mapper.updateMapper(branch, dto);

        return mapper.toBranchResponseDTO(branch);
    }

    @Transactional
    public void deleteBranch(String name){
        
        Branch branch = branchRepository
            .findByName(name)
            .orElseThrow(() -> new BranchNotFoundException(name));

        if(studentRepository.existsByBranchId(branch.getId())){
            throw new BadRequestException(
                "Cannot delete brnach because students are assigned to it"
            );
        }
        
        branchRepository.delete(branch);
    }
}
