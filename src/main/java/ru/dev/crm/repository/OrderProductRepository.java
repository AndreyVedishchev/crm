package ru.dev.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dev.crm.models.OrderProduct;
import ru.dev.crm.models.OrderProductKey;

import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductKey> {
    Optional<OrderProduct> findByOrder_IdAndProduct_Id(Integer orderId, Integer productId);
}
