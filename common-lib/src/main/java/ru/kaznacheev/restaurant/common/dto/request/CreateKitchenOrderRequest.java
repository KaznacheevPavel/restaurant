package ru.kaznacheev.restaurant.common.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * DTO для запроса на создание нового заказа на кухне.
 */
@Getter
@Builder
public class CreateKitchenOrderRequest {

    /**
     * Идентификатор заказа у официантов.
     */
    @NotNull(message = "Идентификатор заказа у официантов не должен быть пустым")
    @Min(value = 1, message = "Идентификатор заказа у официантов должен быть больше 0")
    private final Long waiterOrderId;

    /**
     * Заказанные блюда.
     * <p>
     * Ключ - идентификатор блюда, значение - количество порций.
     */
    @NotEmpty(message = "Состав заказа не должен быть пустым")
    private final Map<@Min(value = 1, message = "Идентификатор блюда должен быть больше 0") Long,
            @Min(value = 1, message = "Количество порций должно быть больше 0") Long> dishes;

}
