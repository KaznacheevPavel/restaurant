package ru.kaznacheev.restaurant.common.exception;

import ru.kaznacheev.restaurant.common.dto.exception.ResponseDetailMessages;
import ru.kaznacheev.restaurant.common.dto.exception.ResponseTitle;

import java.util.List;
import java.util.Map;

/**
 * Исключение, выбрасываемое при попытке получения несуществующего блюда.
 */
public class DishNotFoundException extends ExceptionWithData {

    /**
     * Конструктор исключения.
     *
     * @param notFoundedDishes {@link List} несуществующих блюд
     */
    public DishNotFoundException(List<?> notFoundedDishes) {
        super(ResponseTitle.NOT_FOUND.name(),
                ResponseTitle.NOT_FOUND.getStatus(),
                ResponseDetailMessages.ORDER_COMPOSITION_ERROR.getDetail(),
                Map.of("Неверные блюда", notFoundedDishes));
    }

}
