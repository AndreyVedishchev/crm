package ru.dev.crm.mapper;

import org.mapstruct.Mapper;
import ru.dev.crm.controllers.dto.EmployeeDto;
import ru.dev.crm.models.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployee(EmployeeDto employeeDto);
    EmployeeDto toEmployeeDto(Employee employee);
}
