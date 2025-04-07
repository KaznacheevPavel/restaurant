package ru.kaznacheev.restaurant.waiterservice.service;

import java.util.Map;

/**
 * Интерфейс сервиса для работы с позициями заказа.
 */
public interface OrderPositionService {

    /**
     * Добавляет блюда в заказ.
     *
     * @param orderId Идентификатор заказа
     * @param orderComposition Названия блюд и их количество
     */
    void addDishesToOrder(Long orderId, Map<String, Long> orderComposition);

}
