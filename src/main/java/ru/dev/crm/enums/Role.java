package ru.dev.crm.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    MANAGER("менеджер"),
    ADMINISTRATOR("администратор");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
