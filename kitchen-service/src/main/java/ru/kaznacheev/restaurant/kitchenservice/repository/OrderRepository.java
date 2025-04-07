package ru.kaznacheev.restaurant.kitchenservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;

/**
 * Репозиторий для работы с заказами.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
