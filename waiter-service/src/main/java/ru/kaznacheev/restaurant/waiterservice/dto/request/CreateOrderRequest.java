package ru.kaznacheev.restaurant.waiterservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * DTO для запроса на создание нового заказа.
 */
@AllArgsConstructor
@Getter
public class CreateOrderRequest {

    /**
     * Идентификатор официанта.
     */
    @NotNull(message = "Идентификатор официанта не должен быть пустым")
    @Min(value = 1, message = "Идентификатор официанта должен быть больше 0")
    private final Long waiterId;

    /**
     * Номер стола.
     */
    @NotBlank(message = "Номер стола не должен быть пустым")
    @Size(max = 32, message = "Номер стола не должен быть больше 32 символов")
    private final String tableNumber;

    /**
     * Заказанные блюда.
     * <p>
     * Ключ - название блюда, значение - количество порций.
     */
    @NotEmpty(message = "Состав заказа не должен быть пустым")
    private final Map<@NotBlank(message = "Название блюда не должно быть пустым") String,
            @Min(value = 1, message = "Количество порций должно быть больше 0") Long> dishes;

}
