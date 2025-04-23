package ru.kaznacheev.restaurant.waiterservice.entity;

import lombok.Builder;
import lombok.Getter;

/**
 * Сущность позиции заказа.
 */
@Getter
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
