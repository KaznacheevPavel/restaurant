package ru.kaznacheev.restaurant.waiterservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * DTO для запроса на создание нового заказа.
 */
@Schema(description = "Информация для создания нового заказа")
@AllArgsConstructor
@Getter
@Builder
public class CreateOrderRequest {

    /**
     * Идентификатор официанта.
     */
    @Schema(description = "Идентификатор официанта", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Идентификатор официанта не должен быть пустым")
    @Min(value = 1, message = "Идентификатор официанта должен быть больше 0")
    private final Long waiterId;

    /**
     * Номер стола.
     */
    @Schema(description = "Номер стола", example = "3a", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Номер стола не должен быть пустым")
    @Size(max = 32, message = "Номер стола не должен быть больше 32 символов")
    private final String tableNumber;

    /**
     * Заказанные блюда.
     * <p>
     * Ключ - название блюда, значение - количество порций.
     */
    @Schema(description = "Заказанные блюда",
            example = "\"{\\\"Паста карбонара\\\": 1, \\\"Борщ с пампушками\\\": 3}\"",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Состав заказа не должен быть пустым")
    private final Map<@NotBlank(message = "Название блюда не должно быть пустым") String,
            @Min(value = 1, message = "Количество порций должно быть больше 0") Long> dishes;

}
