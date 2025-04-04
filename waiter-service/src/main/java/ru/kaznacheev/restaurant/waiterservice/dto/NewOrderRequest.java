package ru.kaznacheev.restaurant.waiterservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class NewOrderRequest {

    /**
     * Идентификатор официанта.
     */
    @Min(value = 1, message = "Идентификатор официанта должен быть больше 0")
    private final Long waiterId;

    /**
     * Номер стола.
     */
    @NotBlank(message = "Номер стола не может быть пустым")
    private final String tableNumber;

    /**
     * Состав заказа.
     * <p>
     * Ключ - название блюда, значение - количество порций.
     */
    @NotEmpty(message = "Состав заказа не может быть пустым")
    @DishAmountGreaterThanZero
    private final Map<String, Long> dishes;

}
