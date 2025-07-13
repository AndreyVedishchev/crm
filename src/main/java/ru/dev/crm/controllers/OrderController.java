package ru.dev.crm.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dev.crm.controllers.dto.ApiResponse;
import ru.dev.crm.controllers.dto.OrderDto;
import ru.dev.crm.enums.Status;
import ru.dev.crm.exceptions.OrderValidationException;
import ru.dev.crm.service.OrderService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> create(@RequestBody OrderDto inputDto) {
        try {
            OrderDto orderDto = orderService.create(inputDto);
            ApiResponse<OrderDto> apiResponse = new ApiResponse<>(orderDto);
            apiResponse.setAction("Заказ с id " + orderDto.getId() + " добавлен.");
            apiResponse.setSuccess(true);
            return ResponseEntity.ok(apiResponse);
        } catch (OrderValidationException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessages()));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<OrderDto>> update(@RequestBody OrderDto inputDto) {
        try {
            OrderDto orderDto = orderService.update(inputDto);
            ApiResponse<OrderDto> apiResponse = new ApiResponse<>(orderDto);
            apiResponse.setAction("Заказ с id " + orderDto.getId() + " обновлен.");
            apiResponse.setSuccess(true);
            return ResponseEntity.ok(apiResponse);
        } catch (OrderValidationException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessages()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(List.of(e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> delete(@PathVariable Integer id) {
        try {
            orderService.delete(id);
            ApiResponse<OrderDto> apiResponse = new ApiResponse<>(null);
            apiResponse.setAction("Заказ с id " + id + " удален.");
            apiResponse.setSuccess(true);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(List.of(e.getMessage())));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getClient(@PathVariable Integer id) {
        try {
            OrderDto orderDto = orderService.get(id);
            ApiResponse<OrderDto> apiResponse = new ApiResponse<>(orderDto);
            apiResponse.setAction("Заказ с id " + orderDto.getId() + " найден.");
            apiResponse.setSuccess(true);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(List.of(e.getMessage())));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderDto>>> search(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "true") boolean desc
    ) {
        try {
            Page<OrderDto> result = orderService.search(status, date, page, size, desc);
            ApiResponse<Page<OrderDto>> apiResponse = new ApiResponse<>(result);
            apiResponse.setAction("Результат поиска по нескольким параметрам.");
            apiResponse.setSuccess(true);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(List.of(e.getMessage())));
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ApiResponse<OrderDto>> addProduct(
            @RequestParam Integer orderId, @RequestParam Integer productId, @RequestParam int quantity
    ) {
        OrderDto orderDto = orderService.addProduct(orderId, productId, quantity);
        ApiResponse<OrderDto> apiResponse = new ApiResponse<>(orderDto);
        apiResponse.setAction("В заказ " + orderId + " добавлен продукт " + productId + " в кол-ве " + quantity + " шт.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ApiResponse<OrderDto>> updateProduct(
            @RequestParam Integer orderId, @RequestParam Integer productId, @RequestParam int quantity
    ) {
        OrderDto orderDto = orderService.updateProduct(orderId, productId, quantity);
        ApiResponse<OrderDto> apiResponse = new ApiResponse<>(orderDto);
        apiResponse.setAction("В заказe " + orderId + " обновлено кол-во продукта " + productId + ", новое значение " + quantity + " шт.");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<ApiResponse<OrderDto>> updateProduct(@RequestParam Integer orderId, @RequestParam Integer productId) {
        orderService.deleteProduct(orderId, productId);
        ApiResponse<OrderDto> apiResponse = new ApiResponse<>(null);
        apiResponse.setAction("Из заказа " + orderId + " удален продукт " + productId);
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }
}
