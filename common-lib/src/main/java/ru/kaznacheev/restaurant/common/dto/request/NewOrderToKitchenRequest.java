package ru.kaznacheev.restaurant.common.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * DTO, содержащий информацию о новом заказе.
 */
@AllArgsConstructor
@Getter
@Builder
public class NewOrderToKitchenRequest {

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
    private final Map< @Min(value = 1, message = "Идентификатор заказа должен быть больше 0") Long,
            @Min(value = 1, message = "Количество порций должно быть больше 0") Long> dishes;

}
