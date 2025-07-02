package ru.dev.crm.controllers.dto;

import lombok.Data;
import java.util.List;

@Data
public class ClientDto {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private List<OrderDto> orders;
}
