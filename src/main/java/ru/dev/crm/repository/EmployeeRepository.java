package ru.dev.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dev.crm.models.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findByEmail(String email);
    Optional<Employee> findByNameAndSurnameAndEmailAndRole(String name, String surname , String email, String role);
    List<Employee> findAllByOrderByNameAscRoleAsc();
    Page<Employee> findAll(Pageable pageable);
}
