package ru.kaznacheev.restaurant.kitchenservice.exception;

import lombok.Getter;
import ru.kaznacheev.restaurant.common.dto.exception.ResponseDetailMessages;
import ru.kaznacheev.restaurant.common.dto.exception.ResponseTitle;
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
                ResponseDetailMessages.INSUFFICIENT_DISH_ERROR.getDetail(),
                Map.of("Идентификаторы недостаточных блюд", insufficientDishes));
    }

}
