package ru.dev.crm.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Table(name = "clients")
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String surname;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "client", targetEntity = Order.class/*, cascade = CascadeType.ALL*/)
    private List<Order> orders;
}
