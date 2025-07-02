package ru.dev.crm.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    NEW("новый"),
    PROCESSING("в работе"),
    COMPLETED("выполнен"),
    CANCELED("отменен");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
