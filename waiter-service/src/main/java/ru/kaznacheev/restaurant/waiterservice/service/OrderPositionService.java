package ru.kaznacheev.restaurant.waiterservice.service;

import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс сервиса для работы с позициями заказов.
 */
public interface OrderPositionService {

    /**
     * Создает позиции для заказа.
     *
     * @param orderId Идентификатор заказа
     * @param composition Названия заказанных блюд и количество порций
     * @return {@link List} {@link OrderPositionResponse} с информацией о позиции заказа
     */
    List<OrderPositionResponse> createOrderComposition(Long orderId, Map<String, Long> composition);

}
