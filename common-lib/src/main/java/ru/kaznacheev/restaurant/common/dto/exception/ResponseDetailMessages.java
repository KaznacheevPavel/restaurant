package ru.kaznacheev.restaurant.common.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Детальные сообщения для ответов
 */
@AllArgsConstructor
@Getter
public enum ResponseDetailMessages {
    VALIDATION_ERROR("Ошибка валидации"),
    ORDER_COMPOSITION_ERROR("Неверный состав заказа"),
    INSUFFICIENT_DISH_ERROR("Недостаточное количество порций");

    private final String detail;
}
