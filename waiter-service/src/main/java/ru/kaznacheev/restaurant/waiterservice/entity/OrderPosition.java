package ru.kaznacheev.restaurant.waiterservice.entity;

import lombok.Builder;

/**
 * Сущность позиции заказа.
 */
@Builder
public class OrderPosition {

    /**
     * Идентификатор позиции заказа.
     */
    private Long id;

    /**
     * Идентификатор заказа.
     */
    private Long orderId;

    /**
     * Идентификатор блюда.
     */
    private Long dishId;

    /**
     * Количество порций.
     */
    private Long amount;

}
