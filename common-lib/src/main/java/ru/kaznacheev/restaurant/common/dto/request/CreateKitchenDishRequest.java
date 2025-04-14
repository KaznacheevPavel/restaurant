package ru.kaznacheev.restaurant.common.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO для запроса на создание нового блюда на кухне.
 */
@Getter
@Builder
public class CreateKitchenDishRequest {

    /**
     * Идентификатор блюда.
     */
    @NotNull(message = "Идентификатор блюда не должен быть пустым")
    @Min(value = 1, message = "Идентификатор блюда должен быть больше 0")
    private final Long id;

    /**
     * Сокращенное название блюда.
     */
    @NotBlank(message = "Сокращенное название блюда не должно быть пустым")
    @Size(max = 32, message = "Сокращенное название блюда не должно быть больше 32 символов")
    private final String shortName;

    /**
     * Состав блюда.
     */
    @NotBlank(message = "Состав блюда не должен быть пустым")
    @Size(max = 255, message = "Состав блюда не должен быть больше 255 символов")
    private final String composition;

}
