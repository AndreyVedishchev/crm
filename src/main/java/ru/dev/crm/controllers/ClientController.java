package ru.dev.crm.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dev.crm.controllers.dto.ApiResponse;
import ru.dev.crm.controllers.dto.ClientDto;
import ru.dev.crm.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClientDto>> create(@RequestBody ClientDto inputDto) {
        ClientDto clientDto = clientService.create(inputDto);
        ApiResponse<ClientDto> apiResponse = new ApiResponse<>(clientDto);
        apiResponse.setAction("Клиент с id " + clientDto.getId() + " добавлен.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ClientDto>> update(@RequestBody ClientDto inputDto) {
        ClientDto clientDto = clientService.update(inputDto);
        ApiResponse<ClientDto> apiResponse = new ApiResponse<>(clientDto);
        apiResponse.setAction("Клиент с id " + inputDto.getId() + " обновлен.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientDto>> delete(@PathVariable Integer id) {
        clientService.delete(id);
        ApiResponse<ClientDto> apiResponse = new ApiResponse<>(null);
        apiResponse.setAction("Клиент с id " + id + " удален.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientDto>> getClient(@PathVariable Integer id) {
        ClientDto clientDto = clientService.get(id);
        ApiResponse<ClientDto> apiResponse = new ApiResponse<>(clientDto);
        apiResponse.setAction("Клиент с id " + clientDto.getId() + " найден.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ClientDto>>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "true") boolean asc
    ) {
        Page<ClientDto> result = clientService.search(name, surname, email, phone, page, size, asc);
        ApiResponse<Page<ClientDto>> apiResponse = new ApiResponse<>(result);
        apiResponse.setAction("Результат поиска по нескольким параметрам.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }
}
