package ru.dev.crm.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderValidationException extends RuntimeException {
    private final List<String> messages;

    public OrderValidationException(List<String> messages) {
        super("Order validation failed");
        this.messages = messages;
    }
}
