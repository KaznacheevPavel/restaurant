package ru.kaznacheev.restaurant.waiterservice.service;

import ru.kaznacheev.restaurant.waiterservice.entity.OrderPosition;

import java.util.List;
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

    /**
     * Получает все позиции заказа по его идентификатору.
     *
     * @param orderId Идентификатор заказа
     * @return {@link List} {@link OrderPosition} с информацией о позиции заказа
     */
    List<OrderPosition> getAllOrderPositionsByOrderId(Long orderId);

}
