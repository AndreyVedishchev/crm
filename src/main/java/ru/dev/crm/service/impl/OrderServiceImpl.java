package ru.dev.crm.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.dev.crm.controllers.dto.OrderDto;
import ru.dev.crm.enums.Status;
import ru.dev.crm.mapper.OrderMapper;
import ru.dev.crm.models.Order;
import ru.dev.crm.repository.OrderRepository;
import ru.dev.crm.service.OrderService;
import ru.dev.crm.specification.OrderSpecification;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        Order order = orderMapper.toOrder(orderDto);
        order.setDate(LocalDate.now());
        Order save = orderRepository.save(order);
        return orderMapper.toOrderDto(save);
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        Order order = orderMapper.toOrder(orderDto);
        Optional<Order> byId = orderRepository.findById(order.getId());
        if (byId.isPresent()) {
            Order ord = byId.get();
            ord.setStatus(order.getStatus());
            ord.setClient(order.getClient());
            Order save = orderRepository.save(ord);
            return orderMapper.toOrderDto(save);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Не найден заказ с id " + id);
        }
    }

    @Override
    public OrderDto get(Integer id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isPresent()) {
            return orderMapper.toOrderDto(byId.get());
        } else {
            throw new RuntimeException("Не найден заказ с id " + id);
        }
    }

    @Override
    public Page<OrderDto> search(Status status, LocalDate date, int page, int size, boolean desc) {
        Specification<Order> spec = OrderSpecification.hasStatus(status).and(OrderSpecification.hasDate(date));
        PageRequest req;
        if (desc) {
            Sort sort = Sort.by("date").descending();
            req = PageRequest.of(page, size, sort);
        } else {
            req = PageRequest.of(page, size);
        }
        Page<Order> allOrders = orderRepository.findAll(spec, req);
        return allOrders.map(orderMapper::toOrderDto);
    }
}
