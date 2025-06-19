package ru.dev.crm.controllers.dto;

import lombok.Data;
import ru.dev.crm.enums.Role;

@Data
public class EmployeeDto {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
}
