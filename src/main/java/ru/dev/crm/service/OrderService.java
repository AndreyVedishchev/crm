package ru.dev.crm.service;

import org.springframework.data.domain.Page;
import ru.dev.crm.controllers.dto.OrderDto;
import ru.dev.crm.enums.Status;

import java.time.LocalDate;

public interface OrderService {
    OrderDto create(OrderDto orderDto);
    OrderDto update(OrderDto orderDto);
    void delete(Integer id);
    OrderDto get(Integer id);
    Page<OrderDto> search(Status status, LocalDate date, int page, int size, boolean asc);
}
