package ru.kaznacheev.restaurant.common.exception;

import ru.kaznacheev.restaurant.common.dto.response.ResponseTitle;

/**
 * Исключение, выбрасываемое при попытке получения несуществующего заказа.
 */
public class OrderNotFoundException extends BaseException {
    /**
     * Создает экземпляр класса {@link OrderNotFoundException} с указанными значениями для {@link BaseException}.
     *
     * @param id Идентификатор заказа, который вызвал исключение
     */
    public OrderNotFoundException(int id) {
        super(ResponseTitle.NOT_FOUND.name(), ResponseTitle.NOT_FOUND.getStatus(), "Заказ с идентификатором " + id + " не найден");
    }
}
