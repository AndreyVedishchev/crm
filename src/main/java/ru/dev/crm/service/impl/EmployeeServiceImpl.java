package ru.dev.crm.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;
import ru.dev.crm.controllers.dto.EmployeeDto;
import ru.dev.crm.enums.Role;
import ru.dev.crm.exceptions.EmployeeValidationException;
import ru.dev.crm.mapper.EmployeeMapper;
import ru.dev.crm.models.Employee;
import ru.dev.crm.repository.EmployeeRepository;
import ru.dev.crm.service.EmployeeService;
import ru.dev.crm.specification.EmployeeSpecification;
import ru.dev.crm.util.EmployeeValidator;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeValidator validator;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, EmployeeValidator validator) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.validator = validator;
    }

    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEmployee(employeeDto);

        DataBinder binder = new DataBinder(employee);
        binder.setValidator(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            List<String> messages = binder.getBindingResult().getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new EmployeeValidationException(messages);
        }
        Employee save = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(save);
    }

    @Override
    public EmployeeDto update(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEmployee(employeeDto);

        DataBinder binder = new DataBinder(employee);
        binder.setValidator(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            List<String> messages = binder.getBindingResult().getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new EmployeeValidationException(messages);
        }
        Employee emplById = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найден сотрудник с id " + employee.getId()));
        emplById.setName(employee.getName());
        emplById.setSurname(employee.getSurname());
        emplById.setEmail(employee.getEmail());
        emplById.setPassword(employee.getPassword());
        emplById.setRole(employee.getRole());
        Employee update = employeeRepository.save(emplById);
        return employeeMapper.toEmployeeDto(update);
    }

    @Override
    public void delete(Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Не найден сотрудник с id " + id));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDto get(Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Не найден сотрудник с id " + id));
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public Page<EmployeeDto> search(String name, String surname, String email, String password, Role role, int page, int size, boolean asc) {
        Specification<Employee> spec = EmployeeSpecification.hasName(name)
                                            .and(EmployeeSpecification.hasSurname(surname))
                                            .and(EmployeeSpecification.hasEmail(email))
                                            .and(EmployeeSpecification.hasRole(role));
        PageRequest req;
        if (asc) {
            Sort sort = Sort.by("name").ascending();
            req = PageRequest.of(page, size, sort);
        } else {
            req = PageRequest.of(page, size);
        }
        Page<Employee> allEmployees = employeeRepository.findAll(spec, req);
        return allEmployees.map(employeeMapper::toEmployeeDto);
    }
}
