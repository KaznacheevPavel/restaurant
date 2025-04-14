package ru.kaznacheev.restaurant.kitchenservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPositionCompositeId;

import java.util.List;

/**
 * Репозиторий для работы с позициями заказа.
 */
public interface OrderPositionRepository extends JpaRepository<OrderPosition, OrderPositionCompositeId> {

    /**
     * Возвращает все позиции заказа со связанными блюдами по идентификатору заказа.
     *
     * @param orderId Идентификатор заказа
     * @return {@link List} {@link OrderPosition} с информацией о позиции заказа
     */
    @Query("SELECT op FROM OrderPosition op JOIN FETCH op.dish WHERE op.id.orderId = :orderId")
    List<OrderPosition> findAllWithDishesByOrderId(Long orderId);

}
