package com.aryan.studentmanagementapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import com.aryan.studentmanagementapi.dto.StudentRequestDTO;
import com.aryan.studentmanagementapi.dto.StudentResponseDTO;
import com.aryan.studentmanagementapi.exception.BranchNotFoundException;
import com.aryan.studentmanagementapi.exception.StudentAlreadyExistsException;
import com.aryan.studentmanagementapi.mapper.StudentMapper;
import com.aryan.studentmanagementapi.model.Branch;
import com.aryan.studentmanagementapi.model.Role;
import com.aryan.studentmanagementapi.model.Student;
import com.aryan.studentmanagementapi.model.User;
import com.aryan.studentmanagementapi.repository.BranchRepository;
import com.aryan.studentmanagementapi.repository.StudentRepository;
import com.aryan.studentmanagementapi.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    
    @Mock
    private StudentRepository studentRepository;

    @Mock 
    private BranchRepository branchRepository;

    @Mock
    private UserRepository userRepository;

    @Mock 
    private StudentMapper mapper;

    @InjectMocks
    private StudentService studentService;

    @Test
    void  addStudent_shouldThrowException_whenEmailAlreadyExists() {
        Branch branch = new Branch("CSE");

        Student existingStudent = new Student("Aryan Verma", "aryanverma@gmail.com", branch, 2);

        when(studentRepository.findByEmail(existingStudent.getEmail()))
            .thenReturn(Optional.of(existingStudent));
        
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName("Another name");
        dto.setEmail("aryanverma@gmail.com");
        dto.setYear(2);
        dto.setBranch("CSE");

        assertThrows(
            StudentAlreadyExistsException.class, 
            () -> studentService.addStudent(dto)
        );
    }

    @Test
    void addStudent_shouldReturnStudent_whenValidRequest() {
        Branch branch = new Branch("CSE");

        Student student = new Student("Aryan Verma", "aryanverma@gmail.com", branch, 2);

        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName("Aryan");
        dto.setEmail("aryanverma@gmail.com");
        dto.setYear(2);
        dto.setBranch("CSE");

        StudentResponseDTO responseDTO = new StudentResponseDTO(
            1, 
            "Aryan Verma",
            "CSE", 
            2
        );

        when(branchRepository.findByName(dto.getBranch()))
            .thenReturn(Optional.of(branch));
        
        when(mapper.toStudent(dto, branch))
            .thenReturn(student);

        when(studentRepository.save(student))
            .thenReturn(student);

        when(mapper.toStudentResponseDTO(student))
            .thenReturn(responseDTO);
        
        StudentResponseDTO actualResponseDTO = studentService.addStudent(dto);

        assertEquals("Aryan Verma", actualResponseDTO.getName());
        assertEquals("CSE", actualResponseDTO.getBranch());
        assertEquals(2, actualResponseDTO.getYear());

        verify(studentRepository).save(student);
    }

    @Test
    void addStudent_shouldThrowException_whenBranchNotFound() {

        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName("Aryan");
        dto.setEmail("aryanverma@gmail.com");
        dto.setYear(2);
        dto.setBranch("CSE");

        when(branchRepository.findByName(dto.getBranch()))
            .thenReturn(Optional.empty());

        assertThrows(
            BranchNotFoundException.class, 
            () -> studentService.addStudent(dto)
        );
    }

    @Test
    void getStudent_shouldReturnStudent_whenAdminAccess() {

        Branch branch = new Branch("CSE");

        User user = new User(
            "admin",
            "password",
            Role.ADMIN
        );

        Student student = new Student("Aryan Verma", 
            "aryanverma@gmail.com", 
            branch, 
            2
        );

        StudentResponseDTO responseDTO = new StudentResponseDTO(
            1, 
            "Aryan Verma",
            "CSE", 
            2
        );

        when(userRepository.findByUsername("admin"))
            .thenReturn(Optional.of(user));
        
        when(studentRepository.findById(1))
            .thenReturn(Optional.of(student));
        
        when(mapper.toStudentResponseDTO(student))
            .thenReturn(responseDTO);
        
        StudentResponseDTO actualResponseDTO = studentService.getStudent(1, user.getUsername());

        assertEquals(responseDTO, actualResponseDTO);

        verify(userRepository).findByUsername("admin");
        verify(studentRepository).findById(1);
        verify(mapper).toStudentResponseDTO(student);
    }

    @Test
    void getStudent_shouldThrowException_whenStudentAccessOtherStudent() {

        Student student = mock(Student.class);

        when(student.getRegistrationNo())
            .thenReturn(1001);

        User user = new User(
            "aryan",
            "password",
            Role.STUDENT
        );

        user.setStudent(student);

        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));
        
        assertThrows(
            AccessDeniedException.class,
            () -> studentService.getStudent(1002, "aryan")  
        );
    }

    @Test
    void getStudent_shouldThrowException_whenStudentAccessOwnStudent() {

        Student student = mock(Student.class);

        when(student.getRegistrationNo())
            .thenReturn(1001);

        User user = new User(
            "aryan",
            "password",
            Role.STUDENT
        );

        StudentResponseDTO responseDTO = new StudentResponseDTO(
            1001,
            "Aryan Verma",
            "CSE",
            2
        );

        user.setStudent(student);

        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

        when(studentRepository.findById(1001))
            .thenReturn(Optional.of(student));

        when(mapper.toStudentResponseDTO(student))
            .thenReturn(responseDTO);
        
        StudentResponseDTO actualResponseDTO = assertDoesNotThrow(() -> studentService.getStudent(1001, "aryan"));
        
        assertEquals("Aryan Verma", actualResponseDTO.getName());
        assertEquals("CSE", actualResponseDTO.getBranch());
        assertEquals(2, actualResponseDTO.getYear());

        verify(userRepository).findByUsername("aryan");
        verify(studentRepository).findById(1001);
        verify(mapper).toStudentResponseDTO(student);
    }
}
