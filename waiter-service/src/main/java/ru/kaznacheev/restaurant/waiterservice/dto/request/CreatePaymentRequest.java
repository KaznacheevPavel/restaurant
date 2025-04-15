package ru.kaznacheev.restaurant.waiterservice.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.restaurant.waiterservice.constraint.ValidEnum;
import ru.kaznacheev.restaurant.waiterservice.entity.PaymentType;

/**
 * DTO для запроса на создание нового платежа.
 */
@AllArgsConstructor
@Getter
public class CreatePaymentRequest {

    /**
     * Идентификатор заказа.
     */
    @NotNull(message = "Идентификатор заказа не должен быть пустым")
    @Min(value = 1, message = "Идентификатор заказа должен быть больше 0")
    private final Long orderId;

    /**
     * Тип платежа.
     */
    @NotBlank(message = "Тип платежа не должен быть пустым")
    @ValidEnum(enumClass = PaymentType.class, message = "Тип платежа должен быть CARD или CASH")
    private final String type;

    /**
     * Сумма платежа.
     */
    @Positive(message = "Сумма платежа должна быть положительным числом")
    @Digits(integer = 6, fraction = 2, message = "Сумма платежа может содержать 6 целых и 2 десятичных знака")
    private final String sum;

}
