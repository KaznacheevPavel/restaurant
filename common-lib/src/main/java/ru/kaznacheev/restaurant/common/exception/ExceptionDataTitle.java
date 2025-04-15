package ru.kaznacheev.restaurant.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Дополнительная информация для исключений.
 */
@AllArgsConstructor
@Getter
public enum ExceptionDataTitle {

    NOT_FOUNDED_DISHES("Список не найденных блюд"),
    INSUFFICIENT_DISHES("Идентификаторы блюд с недостаточным количеством порций");

    private final String title;

}
