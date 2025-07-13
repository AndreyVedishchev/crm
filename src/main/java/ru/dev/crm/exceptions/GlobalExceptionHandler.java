package ru.dev.crm.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.dev.crm.controllers.dto.ApiResponse;
import ru.dev.crm.models.Product;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse<?>> handleHttpNotReadable(Exception ex) {
        if (ex.getCause() != null && ex.getCause().getMessage().contains("ru.dev.crm.enums.Role")) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(List.of("Допустимые значения: администратор, менеджер")));
        }
        if (ex.getCause() != null && ex.getCause().getMessage().contains("ru.dev.crm.enums.Status")) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(List.of("Допустимые значения: новый, в работе, выполнен, отменен")));
        }
        return ResponseEntity.badRequest().body(new ApiResponse<>(List.of("Ошибка в формате запроса: " + ex.getMessage())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFoundException(Exception ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(List.of(ex.getMessage())));
    }

//    @ExceptionHandler(ClientValidationException.class)
//    public ResponseEntity<ApiResponse<Client>> handleClientValidationException(Exception ex) {
//        return ResponseEntity.badRequest().body(new ApiResponse<>(List.of(ex.getMessage())));
//    }
//
//    @ExceptionHandler(EmployeeValidationException.class)
//    public ResponseEntity<ApiResponse<Employee>> handleEmployeeValidationException(Exception ex) {
//        return ResponseEntity.badRequest().body(new ApiResponse<>(List.of(ex.getMessage())));
//    }
//
//    @ExceptionHandler(OrderValidationException.class)
//    public ResponseEntity<ApiResponse<Order>> handleOrderValidationException(Exception ex) {
//        return ResponseEntity.badRequest().body(new ApiResponse<>(List.of(ex.getMessage())));
//    }

    @ExceptionHandler(ProductValidationException.class)
    public ResponseEntity<ApiResponse<Product>> handleProductValidationException(ProductValidationException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessages()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(List.of(ex.getMessage())));
    }
}
