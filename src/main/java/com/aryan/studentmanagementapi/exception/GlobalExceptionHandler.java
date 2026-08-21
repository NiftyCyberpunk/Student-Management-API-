package com.aryan.studentmanagementapi.exception;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> buildError(HttpStatus status, String message){
        ApiError error = new ApiError(status.value(), message, Collections.emptyList());

        return ResponseEntity
            .status(status)
            .body(error);
    }
    
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiError> handleStudentNotFound(StudentNotFoundException ex){

        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleStudentAlreadyExists(StudentAlreadyExistsException ex){
        
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<ApiError> handleBranchNotFound(BranchNotFoundException ex){

        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BranchAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleBranchAlreadyExistsException(BranchAlreadyExistsException ex){
        
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex){
        
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex){
        
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ApiError> handleRefreshTokenExpiredException(RefreshTokenExpiredException ex){
        
        return buildError(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(RefreshTokenRevokedException.class)
    public ResponseEntity<ApiError> handleRefreshTokenRevokedException(RefreshTokenRevokedException ex){
        
        return buildError(HttpStatus.UNAUTHORIZED, ex.getMessage());
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

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ApiError> handleInvalidRoleException(InvalidRoleException ex){
        
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(LastAdminException.class)
    public ResponseEntity<ApiError> handleLastAdminException(LastAdminException ex){
        
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AdminSelfDeleteException.class)
    public ResponseEntity<ApiError> handleAdminSelfDeleteException(AdminSelfDeleteException ex){
        
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex){
        
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UpdateRequestNotFoundException.class)
    public ResponseEntity<ApiError> handleUpdateRequestNotFoundException(UpdateRequestNotFoundException ex){
        
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex){
        
        return buildError(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedException(Exception ex){
        
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.");
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiError> handleDisabledException(Exception ex){
        
        return buildError(HttpStatus.UNAUTHORIZED, "User is not registered.");
    }
}

