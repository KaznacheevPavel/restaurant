package ru.kaznacheev.restaurant.waiterservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Исключение, выбрасываемое при попытке получения несуществующего заказа.
 */
@Getter
public class OrderNotFoundException extends BaseException {
    /**
     * Создает экземпляр класса {@link OrderNotFoundException} с указанными значениями для {@link BaseException}.
     *
     * @param id Идентификатор заказа, который вызвал исключение
     */
    public OrderNotFoundException(int id) {
        super("NOT_FOUND", HttpStatus.NOT_FOUND, "Заказ с идентификатором " + id + " не найден");
    }
}
