package ru.dev.crm.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dev.crm.controllers.dto.ApiResponse;
import ru.dev.crm.controllers.dto.ProductDto;
import ru.dev.crm.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> create(@RequestBody ProductDto inputDto) {
        ProductDto productDto = service.create(inputDto);
        ApiResponse<ProductDto> apiResponse = new ApiResponse<>(productDto);
        apiResponse.setAction("Продукт с id " + productDto.getId() + " добавлен.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProductDto>> update(@RequestBody ProductDto inputDto) {
        ProductDto productDto = service.update(inputDto);
        ApiResponse<ProductDto> apiResponse = new ApiResponse<>(productDto);
        apiResponse.setAction("Продукт с id " + productDto.getId() + " обновлен.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> delete(@PathVariable Integer id) {
        service.delete(id);
        ApiResponse<ProductDto> apiResponse = new ApiResponse<>(null);
        apiResponse.setAction("Продукт с id " + id + " удален.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> get(@PathVariable Integer id) {
        ProductDto productDto = service.get(id);
        ApiResponse<ProductDto> apiResponse = new ApiResponse<>(productDto);
        apiResponse.setAction("Продукт с id " + productDto.getId() + " найден.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDto>>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "true") boolean desc
    ) {
        Page<ProductDto> search = service.search(name, price, page, size, desc);
        ApiResponse<Page<ProductDto>> apiResponse = new ApiResponse<>(search);
        apiResponse.setAction("Результат поиска по нескольким параметрам.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }
}

