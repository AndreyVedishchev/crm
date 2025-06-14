package ru.dev.crm.service;

import org.springframework.data.domain.Page;
import ru.dev.crm.models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee update(Employee employee);
    void delete(Integer id);
    Optional<Employee> get(Integer id);
    List<Employee> getAll();
    Optional<Employee> getBy4Fields(String name, String surname ,String email, String role);
    List<Employee> getSorted();
    Page<Employee> getEmployeePage(int page, int size);
}
