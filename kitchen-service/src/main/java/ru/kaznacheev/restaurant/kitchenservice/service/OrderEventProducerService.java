package ru.kaznacheev.restaurant.kitchenservice.service;

/**
 * Интерфейс сервиса для отправки информации о событиях заказа.
 */
public interface OrderEventProducerService {

    /**
     * Отправляет событие о завершении заказа.
     *
     * @param orderId Идентификатор заказа
     */
    void sendOrderCompletedEvent(Long orderId);

    /**
     * Отправляет событие об отмене заказа.
     *
     * @param orderId Идентификатор заказа
     */
    void sendOrderRejectedEvent(Long orderId);

}
