package ru.dev.crm.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ClientValidationException extends RuntimeException {
    private final List<String> messages;

    public ClientValidationException(List<String> messages) {
        super("Client validation failed");
        this.messages = messages;
    }
}
