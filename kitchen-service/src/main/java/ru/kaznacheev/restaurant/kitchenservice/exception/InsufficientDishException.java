package ru.kaznacheev.restaurant.kitchenservice.exception;

import lombok.Getter;
import ru.kaznacheev.restaurant.common.dto.response.ResponseTitle;
import ru.kaznacheev.restaurant.common.exception.ExceptionWithData;

import java.util.List;
import java.util.Map;

/**
 * Исключение, выбрасываемое при недостаточном количестве порций.
 */
@Getter
public class InsufficientDishException extends ExceptionWithData {

    /**
     * Конструктор исключения.
     *
     * @param insufficientDishes Идентификаторы блюд с недостаточным количеством порций.
     */
    public InsufficientDishException(List<Long> insufficientDishes) {
        super(ResponseTitle.CONFLICT.name(),
                ResponseTitle.CONFLICT.getStatus(),
                "Недостаточное количество порций",
                Map.of("Идентификаторы недостаточных блюд", insufficientDishes));
    }

}
