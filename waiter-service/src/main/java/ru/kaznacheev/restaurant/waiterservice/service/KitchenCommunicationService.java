package ru.kaznacheev.restaurant.waiterservice.service;

import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;

/**
 * Интерфейс сервиса для взаимодействия с сервисом кухни.
 */
public interface KitchenCommunicationService {

    /**
     * Отправляет блюдо на кухню.
     *
     * @param request DTO с информацией о блюде
     */
    void sendDishToKitchen(CreateKitchenDishRequest request);

    /**
     * Отправляет заказ на кухню.
     *
     * @param request DTO с информацией о заказе
     */
    void sendOrderToKitchen(CreateKitchenOrderRequest request);

}
