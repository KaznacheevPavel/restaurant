package ru.kaznacheev.restaurant.kitchenservice.service;

import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс сервиса для работы с позициями заказов.
 */
public interface OrderPositionService {

    /**
     * Добавляет позиции к заказу.
     *
     * @param order Сущность заказа
     * @param composition Идентификаторы заказанных блюд и количество порций
     * @return {@link List} {@link OrderPositionResponse} с информацией о позиции заказа
     */
    List<OrderPositionResponse> addDishesToOrder(Order order, Map<Long, Long> composition);

    /**
     * Возвращает список позиций заказа.
     *
     * @param order Сущность заказа
     * @return {@link List} {@link OrderPositionResponse} с информацией о позиции заказа
     */
    List<OrderPositionResponse> getOrderPositions(Order order);

}
