package ru.dev.crm.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.dev.crm.models.Employee;
import ru.dev.crm.repository.EmployeeRepository;
import ru.dev.crm.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        Optional<Employee> emplById = employeeRepository.findById(employee.getId());
        if (emplById.isPresent()) {
            Employee empl = emplById.get();
            empl.setName(employee.getName());
            empl.setSurname(employee.getSurname());
            empl.setEmail(employee.getEmail());
            empl.setPassword(employee.getPassword());
            empl.setRole(employee.getRole());
            return employeeRepository.save(empl);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Optional<Employee> get(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getAll() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getBy4Fields(String name, String surname, String email, String role) {
        return employeeRepository.findByNameAndSurnameAndEmailAndRole(name, surname, email, role);
    }

    @Override
    public List<Employee> getSorted() {
        return employeeRepository.findAllByOrderByNameAscRoleAsc();
    }

    @Override
    public Page<Employee> getEmployeePage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return employeeRepository.findAll(pageRequest);
    }
}
