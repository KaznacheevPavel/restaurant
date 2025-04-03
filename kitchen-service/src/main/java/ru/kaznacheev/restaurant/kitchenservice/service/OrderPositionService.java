package ru.kaznacheev.restaurant.kitchenservice.service;

import java.util.Map;

/**
 * Интерфейс сервиса для работы со связью заказа и блюд.
 */
public interface OrderPositionService {

    /**
     * Добавляет блюда в заказ.
     *
     * @param orderId Идентификатор заказа
     * @param orderComposition Идентификаторы блюд и их количество
     */
    void addDishesToOrder(Long orderId, Map<Long, Long> orderComposition);

}
