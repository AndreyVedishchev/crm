package ru.dev.crm.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductValidationException extends RuntimeException {
    private final List<String> messages;

    public ProductValidationException(List<String> messages) {
        super("Product validation failed");
        this.messages = messages;
    }
}
