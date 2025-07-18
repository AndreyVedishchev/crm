package ru.dev.crm.controllers.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}
