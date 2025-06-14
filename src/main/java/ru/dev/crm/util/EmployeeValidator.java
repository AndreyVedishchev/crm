package ru.dev.crm.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.dev.crm.enums.Role;
import ru.dev.crm.models.Employee;
import ru.dev.crm.repository.EmployeeRepository;

import java.util.Objects;

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

        Employee emplByEmail = employeeRepository.findByEmail(emplFromReq.getEmail());
        if (emplByEmail != null && !emplByEmail.getId().equals(emplFromReq.getId())) {
            errors.rejectValue("email", "", "Сотрудник с таким email уже существует.");
        }

        String role = emplFromReq.getRole();
        if (!(Objects.equals(role, "ADMINISTRATOR") || Objects.equals(role, "MANAGER"))) {
            errors.rejectValue("role", "", "Роль должна иметь значение ADMINISTRATOR или MANAGER");
        }

        /**
         * если использовать enum, то до этой проверки не доходит, см. класс Employee
         */
//        Role role = emplFromReq.getRole();
//        if (!(role == Role.ADMINISTRATOR || role == Role.MANAGER)) {
//            errors.rejectValue("role", "", "Роль должна иметь значение ADMINISTRATOR или MANAGER");
//        }
    }
}
