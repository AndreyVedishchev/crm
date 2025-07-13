package ru.dev.crm.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.dev.crm.models.Product;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) -> name == null || name.isBlank() ? cb.conjunction() : cb.equal(root.get("name"), name);
    }

    public static Specification<Product> hasPrice(Double price) {
        return (root, query, cb) -> price == null || price.isNaN() ? cb.conjunction() : cb.equal(root.get("price"), price);
    }
}
