package ru.dev.crm.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.dev.crm.controllers.dto.ApiResponse;

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
}
