package ru.dev.crm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.dev.crm.enums.Role;

@Data
@Table(name = "employee")
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Size(min = 6, message = "Минимальный размер пароля 6 символов.")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    /**
     * если использовать enum, то не доходит до валидации значений, падает здесь с 400 ошибкой, поэтому использую String, см. класс EmployeeValidator
     */
//    @Enumerated(EnumType.STRING)
//    @Column(name = "role")
//    private Role role;
}
