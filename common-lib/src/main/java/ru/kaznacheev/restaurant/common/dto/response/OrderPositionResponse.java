package ru.kaznacheev.restaurant.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для ответа с информацией о позиции заказа.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderPositionResponse {

    /**
     * Идентификатор блюда.
     */
    private Long dishId;

    /**
     * Название блюда.
     */
    private String name;

    /**
     * Количество порций.
     */
    private Long amount;

}
