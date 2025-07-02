package ru.dev.crm.controllers.dto;

import lombok.Data;
import ru.dev.crm.enums.Status;
import java.time.LocalDate;

@Data
public class OrderDto {
    private Integer id;
    private LocalDate date;
    private Status status;
    private Integer clientId;
}
