package ru.kaznacheev.restaurant.kitchenservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPositionCompositeId;

/**
 * Репозиторий для работы с позициями заказа.
 */
public interface OrderPositionRepository extends JpaRepository<OrderPosition, OrderPositionCompositeId> {
}
