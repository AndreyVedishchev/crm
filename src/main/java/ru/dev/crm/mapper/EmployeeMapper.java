package ru.dev.crm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.dev.crm.controllers.dto.EmployeeDto;
import ru.dev.crm.models.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto toEmployeeDto(Employee employee);
    List<EmployeeDto> toEmployeeDtoList(List<Employee> list);
}
