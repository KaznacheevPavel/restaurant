package ru.kaznacheev.restaurant.waiterservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.kaznacheev.restaurant.waiterservice.constraint.ValidEnum;
import ru.kaznacheev.restaurant.waiterservice.entity.PaymentType;

/**
 * DTO для запроса на создание нового платежа.
 */
@Schema(description = "Информация для создания нового платежа")
@AllArgsConstructor
@Getter
@Builder
public class CreatePaymentRequest {

    /**
     * Идентификатор заказа.
     */
    @Schema(description = "Идентификатор заказа", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Идентификатор заказа не должен быть пустым")
    @Min(value = 1, message = "Идентификатор заказа должен быть больше 0")
    private final Long orderId;

    /**
     * Тип платежа.
     */
    @Schema(description = "Тип платежа", examples = {"CARD", "CASH"}, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Тип платежа не должен быть пустым")
    @ValidEnum(enumClass = PaymentType.class, message = "Тип платежа должен быть CARD или CASH")
    private final String type;

    /**
     * Сумма платежа.
     */
    @Schema(description = "Сумма платежа", example = "1541.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive(message = "Сумма платежа должна быть положительным числом")
    @Digits(integer = 6, fraction = 2, message = "Сумма платежа может содержать 6 целых и 2 десятичных знака")
    private final String sum;

}
