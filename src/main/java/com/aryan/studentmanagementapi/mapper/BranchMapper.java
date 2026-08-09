package com.aryan.studentmanagementapi.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.aryan.studentmanagementapi.dto.BranchRequestDTO;
import com.aryan.studentmanagementapi.dto.BranchResponseDTO;
import com.aryan.studentmanagementapi.model.Branch;

@Component
public class BranchMapper {
    
    public Branch toBranch(BranchRequestDTO dto){
        Branch branch = new Branch(
            dto.getName()
        );

        return branch;
    }

    public BranchResponseDTO toBranchResponseDTO(Branch branch){
        BranchResponseDTO dto = new BranchResponseDTO(
            branch.getId(),
            branch.getName()
        );

        return dto;
    }

    public List<BranchResponseDTO> toBranchResponseDTOs(List<Branch> branches){

        List<BranchResponseDTO> dtos = new ArrayList<>();

        for(Branch branch:branches){
            dtos.add(toBranchResponseDTO(branch));
        }
        return dtos;
    }

    public void updateMapper (Branch branch, BranchRequestDTO dto){
        branch.setName(dto.getName());
    }
}
