package ru.kaznacheev.restaurant.waiterservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * Сущность заказа.
 */
@Getter
@Setter
@Builder
public class Order {

    /**
     * Идентификатор заказа.
     */
    private Long id;

    /**
     * Статус заказа из {@link OrderStatus}.
     */
    private OrderStatus status;

    /**
     * Дата и время создания заказа.
     */
    private OffsetDateTime createdAt;

    /**
     * Идентификатор официанта.
     */
    private Long waiterId;

    /**
     * Номер стола.
     */
    private String tableNumber;

}
