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
    public ResponseEntity<ApiError> handleStudentNotFound(StudentNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.NOT_FOUND)
                    .body(error);
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleStudentAlreadyExists(StudentAlreadyExistsException ex) {
        ApiError error = new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.CONFLICT)
                    .body(error);
    }

    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<ApiError> BranchNotFound(BranchNotFoundException ex) {
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

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        ApiError error = new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.CONFLICT)
                    .body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex){
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.NOT_FOUND)
                    .body(error);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ApiError> handleRefreshTokenExpiredException(RefreshTokenExpiredException ex){
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(error);
    }

    @ExceptionHandler(RefreshTokenRevokedException.class)
    public ResponseEntity<ApiError> handleRefreshTokenRevokedException(RefreshTokenRevokedException ex){
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

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

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ApiError> handleRoleNotFoundException(InvalidRoleException ex){
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
    }

    @ExceptionHandler(LastAdminException.class)
    public ResponseEntity<ApiError> handleLastAdminException(LastAdminException ex){
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
    }

    @ExceptionHandler(AdminSelfDeleteException.class)
    public ResponseEntity<ApiError> handleAdminSelfDeleteException(AdminSelfDeleteException ex){
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Collections.emptyList());

        return ResponseEntity                    
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
    }
}
