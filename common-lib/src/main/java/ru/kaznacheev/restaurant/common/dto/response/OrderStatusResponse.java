package ru.kaznacheev.restaurant.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для ответа с информацией о статусе заказа.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatusResponse {

    /**
     * Идентификатор заказа.
     */
    private Long id;

    /**
     * Статус заказа.
     */
    private String status;

}
