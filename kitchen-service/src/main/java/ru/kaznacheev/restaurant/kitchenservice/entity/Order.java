package ru.kaznacheev.restaurant.kitchenservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Класс, представляющий сущность заказа.
 */
@Getter
@Setter
@Builder
public class Order {
    /**
     * Идентификатор заказа.
     */
    private Integer id;

    /**
     * Список блюд в заказе и их количество.
     */
    private Map<String, Integer> dishes;

    /**
     * Комментарий к заказу.
     */
    private String comment;

    /**
     * Статус заказа {@link OrderStatus}.
     */
    private OrderStatus status;
}
