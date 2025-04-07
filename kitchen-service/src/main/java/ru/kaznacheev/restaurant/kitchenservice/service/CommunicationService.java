package ru.kaznacheev.restaurant.kitchenservice.service;

/**
 * Сервис для взаимодействия с сервисом официантов.
 */
public interface CommunicationService {

    /**
     * Завершает заказ для официантов.
     *
     * @param waiterOrderId Идентификатор заказа официанта
     */
    void completeOrderOnWaiter(Long waiterOrderId);

}
