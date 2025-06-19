package ru.dev.crm.service;

import org.springframework.data.domain.Page;
import ru.dev.crm.controllers.dto.EmployeeDto;
import ru.dev.crm.enums.Role;

public interface EmployeeService {
    EmployeeDto create(EmployeeDto employeeDto);
    EmployeeDto update(EmployeeDto employeeDto);
    void delete(Integer id);
    EmployeeDto get(Integer id);
    Page<EmployeeDto> search(String name, String surname, String email, String password, Role role, int page, int size, boolean asc);
}
