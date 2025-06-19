package ru.dev.crm.controllers.dto;

import lombok.Data;
import java.util.List;

@Data
public class ApiResponse<T> {
    private T data;
    private List<String> errors;
    private boolean success;
    private String action;

    public ApiResponse(T data) {
        this.data = data;
        this.success = true;
    }
    public ApiResponse(List<String> errors) {
        this.errors = errors;
        this.success = false;
    }
}
