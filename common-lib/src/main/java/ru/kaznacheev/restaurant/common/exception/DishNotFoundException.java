package ru.kaznacheev.restaurant.common.exception;

import ru.kaznacheev.restaurant.common.dto.response.ResponseTitle;

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
                "Неверный состав заказа",
                Map.of("Неверные блюда", notFoundedDishes));
    }

}
