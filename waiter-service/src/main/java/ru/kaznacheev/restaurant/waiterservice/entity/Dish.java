package ru.kaznacheev.restaurant.waiterservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Сущность блюда.
 */
@Getter
@Setter
@Builder
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
