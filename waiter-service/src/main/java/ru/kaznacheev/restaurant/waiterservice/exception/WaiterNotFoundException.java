package ru.kaznacheev.restaurant.waiterservice.exception;

import ru.kaznacheev.restaurant.common.dto.response.ResponseTitle;
import ru.kaznacheev.restaurant.common.exception.BaseException;

/**
 * Исключение, выбрасываемое при попытке получения несуществующего официанта.
 */
public class WaiterNotFoundException extends BaseException {

    /**
     * Конструктор исключения.
     *
     * @param id Идентификатор официанта, который вызвал исключение
     */
    public WaiterNotFoundException(Long id) {
        super(ResponseTitle.NOT_FOUND.name(), ResponseTitle.NOT_FOUND.getStatus(), "Официант с идентификатором " + id + " не найден");
    }

}
