package ru.dev.crm.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.dev.crm.enums.Role;
import ru.dev.crm.models.Employee;

public class EmployeeSpecification {

    public static Specification<Employee> hasName(String name) {
        return (root, query, cb) -> name == null || name.isBlank() ? null : cb.equal(root.get("name"), name);
    }

    public static Specification<Employee> hasSurname(String surname) {
        return (root, query, cb) -> surname == null || surname.isBlank() ? null : cb.equal(root.get("surname"), surname);
    }

    public static Specification<Employee> hasEmail(String email) {
        return (root, query, cb) -> email == null || email.isBlank() ? null : cb.equal(root.get("email"), email);
    }

    public static Specification<Employee> hasRole(Role role) {
        return (root, query, cb) -> role == null ? null : cb.equal(root.get("role"), role);
    }
}
