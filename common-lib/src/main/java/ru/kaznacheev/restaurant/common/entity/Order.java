package ru.kaznacheev.restaurant.common.entity;

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
    private Integer id;
    private Map<String, Integer> dishes;
    private String comment;
    private OrderStatus status;
}
