package ru.kaznacheev.restaurant.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * DTO для запроса на создание нового заказа на кухне.
 */
@Schema(description = "Информация о новом заказе")
@Getter
@Builder
public class CreateKitchenOrderRequest {

    /**
     * Идентификатор заказа у официантов.
     */
    @Schema(description = "Идентификатор заказа у официантов", example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Идентификатор заказа у официантов не должен быть пустым")
    @Min(value = 1, message = "Идентификатор заказа у официантов должен быть больше 0")
    private final Long waiterOrderId;

    /**
     * Заказанные блюда.
     * <p>
     * Ключ - идентификатор блюда, значение - количество порций.
     */
    @Schema(description = "Заказанные блюда (ключ - идентификатор блюда, значение - количество порций)",
            example = "{\"1\": 1, \"2\": 3}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Состав заказа не должен быть пустым")
    private final Map<@Min(value = 1, message = "Идентификатор блюда должен быть больше 0") Long,
            @Min(value = 1, message = "Количество порций должно быть больше 0") Long> dishes;

}
