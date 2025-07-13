package ru.dev.crm.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dev.crm.controllers.dto.ApiResponse;
import ru.dev.crm.controllers.dto.EmployeeDto;
import ru.dev.crm.enums.Role;
import ru.dev.crm.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDto>> create(@RequestBody EmployeeDto inputDto) {
        EmployeeDto employeeDto = employeeService.create(inputDto);
        ApiResponse<EmployeeDto> apiResponse = new ApiResponse<>(employeeDto);
        apiResponse.setAction("Сотрудник с id " + employeeDto.getId() + " добавлен.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<EmployeeDto>> update(@RequestBody EmployeeDto inputDto) {
        EmployeeDto employeeDto = employeeService.update(inputDto);
        ApiResponse<EmployeeDto> apiResponse = new ApiResponse<>(employeeDto);
        apiResponse.setAction("Сотрудник с id " + employeeDto.getId() + " обновлен.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        ApiResponse<EmployeeDto> apiResponse = new ApiResponse<>(null);
        apiResponse.setAction("Сотрудник с id " + id + " удален.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployee(@PathVariable Integer id) {
        EmployeeDto employeeDto = employeeService.get(id);
        ApiResponse<EmployeeDto> apiResponse = new ApiResponse<>(employeeDto);
        apiResponse.setAction("Сотрудник с id " + employeeDto.getId() + " найден.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EmployeeDto>>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "true") boolean asc
    ) {
        Page<EmployeeDto> result = employeeService.search(name, surname, email, password, role, page, size, asc);
        ApiResponse<Page<EmployeeDto>> apiResponse = new ApiResponse<>(result);
        apiResponse.setAction("Результат поиска по нескольким параметрам.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }
}
