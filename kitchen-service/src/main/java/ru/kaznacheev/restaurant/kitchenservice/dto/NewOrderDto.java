package ru.kaznacheev.restaurant.kitchenservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.restaurant.common.validation.constraint.DishAmountGreaterThanZero;

import java.util.Map;

/**
 * DTO, содержащий информацию о новом заказе.
 */
@AllArgsConstructor
@Getter
public class NewOrderDto {

    /**
     * Идентификатор заказа у официанта.
     */
    @Min(value = 1, message = "Идентификатор официанта должен быть больше 0")
    private final Long waiterOrderId;

    /**
     * Состав заказа.
     * <p>
     * Ключ - идентификатор блюда, значение - количество порций.
     */
    @NotEmpty(message = "Состав заказа не может быть пустым")
    @DishAmountGreaterThanZero
    private final Map<Long, Long> dishes;

}
