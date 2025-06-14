package ru.dev.crm.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.dev.crm.controllers.dto.EmployeeDto;
import ru.dev.crm.mapper.EmployeeMapper;
import ru.dev.crm.models.Employee;
import ru.dev.crm.service.EmployeeService;
import ru.dev.crm.util.EmployeeValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeValidator validator;

    public EmployeeController(EmployeeService employeeService, EmployeeValidator validator) {
        this.employeeService = employeeService;
        this.validator = validator;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid Employee employee, BindingResult bindingResult) {
        validator.validate(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            List<String> messages = allErrors.stream().map(er -> er.getDefaultMessage()).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        Employee empl = employeeService.create(employee);
        EmployeeDto employeeDto = EmployeeMapper.INSTANCE.toEmployeeDto(empl);
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid Employee employee, BindingResult bindingResult) {
        validator.validate(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            List<String> messages = allErrors.stream().map(er -> er.getDefaultMessage()).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        Employee empl = employeeService.update(employee);
        EmployeeDto employeeDto = EmployeeMapper.INSTANCE.toEmployeeDto(empl);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        employeeService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Integer id) {
        Optional<Employee> empl = employeeService.get(id);
        EmployeeDto employeeDto = EmployeeMapper.INSTANCE.toEmployeeDto(empl.get());
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<EmployeeDto> getAll() {
        List<Employee> allEmpl = employeeService.getAll();
        return EmployeeMapper.INSTANCE.toEmployeeDtoList(allEmpl);
    }

    @GetMapping("/By4Fields")
    public ResponseEntity<EmployeeDto> getBy4Fields(@RequestParam String name, @RequestParam String surname
                                                    , @RequestParam String email, @RequestParam String role) {
        Optional<Employee> empl = employeeService.getBy4Fields(name, surname, email, role);
        EmployeeDto employeeDto = EmployeeMapper.INSTANCE.toEmployeeDto(empl.get());
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/allSorted")
    public List<EmployeeDto> getAllSorted() {
        List<Employee> sortedEmpl = employeeService.getSorted();
        return EmployeeMapper.INSTANCE.toEmployeeDtoList(sortedEmpl);
    }

    @GetMapping("/getPage")
    public List<EmployeeDto> getPage(@RequestParam(defaultValue = "0") int page
                                    , @RequestParam(defaultValue = "10") int size) {
        Page<Employee> employeePage = employeeService.getEmployeePage(page, size);
        return EmployeeMapper.INSTANCE.toEmployeeDtoList(employeePage.getContent());
    }
}
