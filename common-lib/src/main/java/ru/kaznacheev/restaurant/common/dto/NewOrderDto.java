package ru.kaznacheev.restaurant.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * DTO для создания нового заказа.
 */
@AllArgsConstructor
@Getter
public class NewOrderDto {
    /**
     * Заказанные блюда.
     * Ключ - название блюда, значение - количество
     */
    private final Map<String, Integer> dishes;

    /**
     * Комментарий к заказу.
     */
    private final String comment;
}
