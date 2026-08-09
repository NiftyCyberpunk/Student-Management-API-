package com.aryan.studentmanagementapi.exception;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiError> handleStudentNotFound(StudentNotFoundException ex){
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.NOT_FOUND)
                    .body(error);
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleStudentAlreadyExists(StudentAlreadyExistsException ex){
        ApiError error = new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.CONFLICT)
                    .body(error);
    }

    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<ApiError> BranchNotFound(BranchNotFoundException ex){
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.NOT_FOUND)
                    .body(error);
    }

    @ExceptionHandler(BranchAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleBranchAlreadyExistsException(BranchAlreadyExistsException ex){
        ApiError error = new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.CONFLICT)
                    .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        List<String> errors = new ArrayList<>();

        BindingResult bindingResult = ex.getBindingResult();

        List <FieldError> fieldErrors = bindingResult.getFieldErrors();

        for(FieldError fieldError:fieldErrors){
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);

        return ResponseEntity                    
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
    }
}
