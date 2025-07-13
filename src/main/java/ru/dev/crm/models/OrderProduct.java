package ru.dev.crm.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "order_product")
@Entity
public class OrderProduct {
    @EmbeddedId
    private OrderProductKey id = new OrderProductKey();

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    private int quantity;
}
