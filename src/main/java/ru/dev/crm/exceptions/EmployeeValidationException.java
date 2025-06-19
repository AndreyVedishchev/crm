package ru.dev.crm.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class EmployeeValidationException extends RuntimeException {
    private final List<String> messages;

    public EmployeeValidationException(List<String> messages) {
        super("Employee validation failed");
        this.messages = messages;
    }
}