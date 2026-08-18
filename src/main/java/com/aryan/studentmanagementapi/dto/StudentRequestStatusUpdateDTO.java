package com.aryan.studentmanagementapi.dto;

import com.aryan.studentmanagementapi.model.UpdateRequestStatus;

import jakarta.validation.constraints.NotNull;

public class StudentRequestStatusUpdateDTO {

    @NotNull
    private UpdateRequestStatus status;

    public UpdateRequestStatus getStatus() {
        return status;
    }

    public void setStatus(UpdateRequestStatus status) {
        this.status = status;
    }
}
