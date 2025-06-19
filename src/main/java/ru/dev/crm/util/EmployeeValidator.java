package ru.dev.crm.util;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.dev.crm.models.Employee;
import ru.dev.crm.repository.EmployeeRepository;
import ru.dev.crm.specification.EmployeeSpecification;

import java.util.Optional;

@Component
public class EmployeeValidator implements Validator {

    private final EmployeeRepository employeeRepository;

    public EmployeeValidator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Employee.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Employee emplFromReq = (Employee) o;

        Specification<Employee> spec = EmployeeSpecification.hasEmail(emplFromReq.getEmail());
        Optional<Employee> optional = employeeRepository.findOne(spec);
        Employee emplByEmail = optional.orElse(null);

        if (emplByEmail != null && !emplByEmail.getId().equals(emplFromReq.getId())) {
            errors.rejectValue("email", "", "Сотрудник с таким email уже существует.");
        }
        if (emplFromReq.getPassword().length() < 6) {
            errors.rejectValue("password", "", "Минимальный размер пароля 6 символов.");
        }
    }
}
