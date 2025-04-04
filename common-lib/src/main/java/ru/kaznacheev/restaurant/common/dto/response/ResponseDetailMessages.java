package ru.kaznacheev.restaurant.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Детальные сообщения для ответов
 */
@AllArgsConstructor
@Getter
public enum ResponseDetailMessages {
    ORDER_CREATED("Заказ успешно создан"),
    ORDER_REJECTED("Заказ успешно отменен"),
    ORDER_COMPLETED("Заказ успешно выполнен"),
    GET_ORDER("Заказ успешно получен"),
    GET_ORDER_LIST("Список заказов успешно получен"),
    GET_STATUS("Статус заказа успешно получен"),
    VALIDATION_ERROR("Ошибка валидации");

    private final String detail;
}
