package ru.dev.crm.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import ru.dev.crm.controllers.dto.EmployeeDto;
import ru.dev.crm.models.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployee(EmployeeDto employeeDto);
    EmployeeDto toEmployeeDto(Employee employee);
//    Page<EmployeeDto> toEmployeeDtoList(Page<Employee> list);
}
