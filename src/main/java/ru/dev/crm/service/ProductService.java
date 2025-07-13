package ru.dev.crm.service;

import org.springframework.data.domain.Page;
import ru.dev.crm.controllers.dto.ProductDto;

public interface ProductService {
    ProductDto create(ProductDto productDto);
    ProductDto update(ProductDto productDto);
    void delete(Integer id);
    ProductDto get(Integer id);
    Page<ProductDto> search(String name, Double price, int page, int size, boolean srt);
}
