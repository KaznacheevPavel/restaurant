package ru.kaznacheev.restaurant.common.exception;

import ru.kaznacheev.restaurant.common.dto.exception.ResponseTitle;

/**
 * Исключение, выбрасываемое при попытке получения несуществующего заказа.
 */
public class OrderNotFoundException extends BaseException {

    /**
     * Конструктор исключения.
     *
     * @param id Идентификатор заказа, который вызвал исключение
     */
    public OrderNotFoundException(Long id) {
        super(ResponseTitle.NOT_FOUND.name(), ResponseTitle.NOT_FOUND.getStatus(), "Заказ с идентификатором " + id + " не найден");
    }

}
