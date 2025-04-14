package ru.kaznacheev.restaurant.waiterservice.service;

import ru.kaznacheev.restaurant.common.dto.kafka.OrderEvent;

/**
 * Интерфейс сервиса для обработки событий заказов.
 */
public interface OrderEventConsumerService {

    /**
     * Обрабатывает событие заказа.
     *
     * @param orderEvent DTO с информацией о событии заказа
     */
    void consumeOrderEvent(OrderEvent orderEvent);

}
