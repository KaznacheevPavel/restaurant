package ru.kaznacheev.restaurant.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO для ответа с информацией о блюде на кухне.
 */
@AllArgsConstructor
@Getter
public class KitchenDishResponse {

    /**
     * Идентификатор блюда.
     */
    private Long id;

    /**
     * Доступное количество порций.
     */
    private Long balance;

    /**
     * Сокращенное название блюда.
     */
    private String shortName;

    /**
     * Состав блюда.
     */
    private String composition;

}
