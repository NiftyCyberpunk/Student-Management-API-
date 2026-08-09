package com.aryan.studentmanagementapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.dto.BranchRequestDTO;
import com.aryan.studentmanagementapi.exception.BranchAlreadyExistsException;
import com.aryan.studentmanagementapi.exception.BranchNotFoundException;
import com.aryan.studentmanagementapi.mapper.BranchMapper;
import com.aryan.studentmanagementapi.model.Branch;
import com.aryan.studentmanagementapi.repository.BranchRepository;

import jakarta.transaction.Transactional;

@Service
public class BranchService {
    
    private final BranchRepository branchRepository;
    private final BranchMapper mapper;

    public BranchService(BranchRepository branchRepository, BranchMapper mapper){
        this.branchRepository = branchRepository;
        this.mapper = mapper;
    }

    public List<Branch> getAllBranchs(){
        return branchRepository.findAll();
    }

    public Branch addBranch(BranchRequestDTO dto){

        branchRepository
            .findByName(dto.getName())
            .ifPresent(existingBranch -> {
                    throw new BranchAlreadyExistsException(
                        "Branch with name " + dto.getName() + " already exists." 
                    );
                }
            );
        
        Branch branch = mapper.toBranch(dto);

        return branchRepository.save(branch);
    }

    @Transactional
    public Branch updateBranch(String name, BranchRequestDTO dto){

        Branch branch = branchRepository
            .findByName(name)
            .orElseThrow(() -> new BranchNotFoundException(name));


        branchRepository
            .findByName(dto.getName())
            .ifPresent(existingBranch -> {
                    throw new BranchAlreadyExistsException(
                        "Branch with name " + dto.getName() + " already exists." 
                    );
                }
            );
        
        mapper.updateMapper(branch, dto);

        return branch;
    }

    public void deleteBranch(String name){
        
        Branch branch = branchRepository
            .findByName(name)
            .orElseThrow(() -> new BranchNotFoundException(name));
        
        branchRepository.delete(branch);
    }
}
