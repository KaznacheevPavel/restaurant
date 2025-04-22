package ru.kaznacheev.restaurant.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Подробное описания для исключений.
 */
@AllArgsConstructor
@Getter
public enum ExceptionDetail {

    WAITER_NOT_FOUND_BY_ID("Официант с идентификатором %d не найден"),

    DISH_NOT_FOUND_BY_ID("Блюдо с идентификатором %d не найдено"),
    DISH_TITLE_ALREADY_EXISTS("Блюдо с названием %s уже существует"),

    ORDER_NOT_FOUND_BY_ID("Заказ с идентификатором %d не найден"),
    ORDER_COMPOSITION_EXCEPTION("Некорректный состав заказа"),
    ORDER_PAYMENT_EXCEPTION("Заказ %d может быть оплачен только в статусе %s"),
    ORDER_COOK_EXCEPTION("Заказ %d может быть приготовлен только в статусе %s"),
    ORDER_REJECT_EXCEPTION("Заказ %d может быть отменен только в статусе %s"),

    PAYMENT_NOT_FOUND_BY_ID("Платеж с идентификатором %d не найден"),
    PAYMENT_AMOUNT_MISMATCH("Неверная сумма оплаты для заказа %d, получено %s, требуется %s"),

    VALIDATION_EXCEPTION("Ошибка валидации");

    private final String template;

    /**
     * Форматирует сообщение об исключении.
     *
     * @param params Параметры для форматирования
     * @return Форматированное сообщение
     */
    public String format(Object... params) {
        return String.format(template, params);
    }

}
