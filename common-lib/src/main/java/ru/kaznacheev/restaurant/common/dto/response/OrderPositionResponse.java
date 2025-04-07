package ru.kaznacheev.restaurant.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO, содержащий информацию о позиции заказа.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderPositionResponse {

    /**
     * Название блюда.
     */
    private String dishName;

    /**
     * Количество порций.
     */
    private Long amount;

}
