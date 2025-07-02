package ru.dev.crm.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.dev.crm.models.Client;

public class ClientSpecification {

    public static Specification<Client> hasName(String name) {
        return (root, query, cb) -> name == null || name.isBlank() ? cb.conjunction() : cb.equal(root.get("name"), name);
    }

    public static Specification<Client> hasSurname(String surname) {
        return (root, query, cb) -> surname == null || surname.isBlank() ? cb.conjunction() : cb.equal(root.get("surname"), surname);
    }

    public static Specification<Client> hasEmail(String email) {
        return (root, query, cb) -> email == null || email.isBlank() ? cb.conjunction() : cb.equal(root.get("email"), email);
    }

    public static Specification<Client> hasPhone(String phone) {
        return (root, query, cb) -> phone == null || phone.isBlank() ? cb.conjunction() : cb.equal(root.get("phone"), phone);
    }
}
