package ru.kaznacheev.restaurant.kitchenservice.service;

import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPosition;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс сервиса для работы со связью заказа и блюд.
 */
public interface OrderPositionService {

    /**
     * Добавляет блюда в заказ.
     *
     * @param order Заказ
     * @param orderComposition Идентификаторы блюд и их количество
     * @return {@link List} {@link OrderPosition} с позицией заказа
     */
    List<OrderPosition> addDishesToOrder(Order order, Map<Long, Long> orderComposition);

}
