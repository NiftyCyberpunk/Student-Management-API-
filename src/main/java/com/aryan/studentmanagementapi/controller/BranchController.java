package com.aryan.studentmanagementapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.studentmanagementapi.dto.BranchRequestDTO;
import com.aryan.studentmanagementapi.dto.BranchResponseDTO;
import com.aryan.studentmanagementapi.dto.BranchStudentCountDTO;
import com.aryan.studentmanagementapi.mapper.BranchMapper;
import com.aryan.studentmanagementapi.model.Branch;
import com.aryan.studentmanagementapi.response.ApiResponse;
import com.aryan.studentmanagementapi.service.BranchService;

import jakarta.validation.Valid;

@RestController
public class BranchController {
    
    private final BranchService branchService;
    private final BranchMapper mapper;

    public BranchController(BranchService branchService, BranchMapper mapper){
        this.branchService = branchService;
        this.mapper = mapper;
    }

    @GetMapping("/branches")
    public ResponseEntity<ApiResponse<List<BranchResponseDTO>>> getAllBranchs(){
        List<Branch> branches = branchService.getAllBranchs();

        List<BranchResponseDTO> dtos = mapper.toBranchResponseDTOs(branches);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Branches fetched successfully.", dtos)
            );
    }

    @GetMapping("/branches/studentCount")
    public ResponseEntity<ApiResponse<List<BranchStudentCountDTO>>> getBranchStudentCount(){

        List<BranchStudentCountDTO> studentCountDtos = branchService.getStudentCount();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Students count with branch fetched successfully.", studentCountDtos)
            );
    }

    @PostMapping("/branches")
    public ResponseEntity<ApiResponse<BranchResponseDTO>> addBranch(@Valid @RequestBody BranchRequestDTO dto){

        BranchResponseDTO responseDto = branchService.addBranch(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Branch added successfully.", responseDto)
            );
    }

    @PutMapping("/branches/{name}")
    public ResponseEntity<ApiResponse<BranchResponseDTO>> updateBranch(@PathVariable String name, @Valid @RequestBody BranchRequestDTO dto){

        BranchResponseDTO responseDto = branchService.updateBranch(name, dto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Branch updated successfully.", responseDto)
            );
    }

    @DeleteMapping("/branches/{name}")
    public ResponseEntity<ApiResponse<BranchResponseDTO>> deleteBranch(@PathVariable String name){

        branchService.deleteBranch(name);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Branch deleted successfully.", null)
            );
    }
}
