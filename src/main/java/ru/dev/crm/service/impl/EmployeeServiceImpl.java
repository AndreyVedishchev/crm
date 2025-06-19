package ru.dev.crm.service.impl;

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
import java.util.Optional;

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
        Optional<Employee> emplById = employeeRepository.findById(employee.getId());
        if (emplById.isPresent()) {
            Employee empl = emplById.get();
            empl.setName(employee.getName());
            empl.setSurname(employee.getSurname());
            empl.setEmail(employee.getEmail());
            empl.setPassword(employee.getPassword());
            empl.setRole(employee.getRole());
            Employee update = employeeRepository.save(empl);
            return employeeMapper.toEmployeeDto(update);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Не найден сотрудник с id " + id);
        }
    }

    @Override
    public EmployeeDto get(Integer id) {
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()) {
            Employee employee = byId.get();
            return employeeMapper.toEmployeeDto(employee);
        } else {
            throw new RuntimeException("Не найден сотрудник с id " + id);
        }
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
