package ru.kaznacheev.restaurant.waiterservice.service;

/**
 * Сервис для взаимодействия с сервисом кухни.
 */
public interface CommunicationService {

    /**
     * Отправляет заказ на кухню.
     *
     * @param orderId Идентификатор заказа
     */
    void sendOrderToKitchen(Long orderId);

}
