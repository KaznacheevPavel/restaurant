package ru.kaznacheev.restaurant.waiterservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO для ответа с информацией о блюде.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishResponse {

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
