package com.aryan.studentmanagementapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.aryan.studentmanagementapi.dto.BranchRequestDTO;
import com.aryan.studentmanagementapi.dto.BranchResponseDTO;
import com.aryan.studentmanagementapi.mapper.BranchMapper;
import com.aryan.studentmanagementapi.model.Branch;
import com.aryan.studentmanagementapi.repository.BranchRepository;
import com.aryan.studentmanagementapi.service.BranchService;
import com.aryan.studentmanagementapi.service.CustomUserDetailsService;
import com.aryan.studentmanagementapi.service.JwtService;

@WebMvcTest(BranchController.class)
public class BranchControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BranchRepository branchRepository;

    @MockitoBean
    private BranchMapper mapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private BranchService branchService;

    @Test
    void getAllBranches_shouldReeturnBranches() throws Exception {
        
        Branch cse = new Branch("CSE");
        Branch ece = new Branch("ECE");

        List<Branch> branches = List.of(cse, ece);

        List<BranchResponseDTO> dtos = List.of(
            new BranchResponseDTO(1, "CSE"),
            new BranchResponseDTO(2, "ECE")
        );

        when(branchService.getAllBranchs())
            .thenReturn(branches);

        when(mapper.toBranchResponseDTOs(branches))
            .thenReturn(dtos);

        mockMvc
            .perform(get("/branches"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Branches fetched successfully."))
            .andExpect(jsonPath("$.data[0].id").value(1))
            .andExpect(jsonPath("$.data[0].name").value("CSE"))
            .andExpect(jsonPath("$.data[1].id").value(2))
            .andExpect(jsonPath("$.data[1].name").value("ECE"));

        verify(branchService).getAllBranchs();
        verify(mapper).toBranchResponseDTOs(branches);
    }

    @Test
    void addBranch_shouldReturnCreatedBranch_whenValidRequest() throws Exception {
        BranchResponseDTO dto = new BranchResponseDTO(1, "CSE");

        when(branchService.addBranch(any(BranchRequestDTO.class)))
            .thenReturn(dto);

        mockMvc
            .perform(post("/branches")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "name": "CSE"
                }
            """)
            
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("Branch added successfully."))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.name").value("CSE"));

        verify(branchService).addBranch(any(BranchRequestDTO.class));
    }
}
