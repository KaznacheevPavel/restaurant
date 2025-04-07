package ru.kaznacheev.restaurant.waiterservice.entity;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Сущность блюда.
 */
@Getter
public class Dish {

    /**
     * Идентификатор блюда.
     */
    private Long id;

    /**
     * Название блюда.
     */
    private String name;

    /**
     * Стоимость блюда.
     */
    private BigDecimal cost;

}
