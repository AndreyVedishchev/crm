package ru.dev.crm.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "products")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double price;
}
