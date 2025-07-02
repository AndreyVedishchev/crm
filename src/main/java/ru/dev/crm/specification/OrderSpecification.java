package ru.dev.crm.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.dev.crm.enums.Status;
import ru.dev.crm.models.Order;
import java.time.LocalDate;

public class OrderSpecification {

    public static Specification<Order> hasStatus(Status status) {
        return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Order> hasDate(LocalDate date) {
        return (root, query, cb) -> date == null ? cb.conjunction() : cb.equal(root.get("date"), date);
    }
}
