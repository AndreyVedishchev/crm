package ru.dev.crm.mapper;

import org.mapstruct.Mapper;
import ru.dev.crm.controllers.dto.ProductDto;
import ru.dev.crm.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDto(Product product);
    Product toProduct(ProductDto productDto);
}
