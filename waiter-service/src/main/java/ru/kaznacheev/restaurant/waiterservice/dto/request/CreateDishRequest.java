package ru.kaznacheev.restaurant.waiterservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO для запроса на создание нового блюда.
 */
@Schema(description = "Информация для создания нового блюда")
@AllArgsConstructor
@Getter
@Builder
public class CreateDishRequest {

    /**
     * Название блюда.
     */
    @Schema(description = "Название блюда", example = "Картофель по-деревенски", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Название блюда не должно быть пустым")
    @Size(max = 64, message = "Название блюда не должно быть больше 64 символов")
    private final String name;

    /**
     * Сокращенное название блюда.
     */
    @Schema(description = "Сокращенное название блюда", example = "По-деревенски", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Сокращенное название блюда не должно быть пустым")
    @Size(max = 32, message = "Сокращенное название блюда не должно быть больше 32 символов")
    private final String shortName;

    /**
     * Состав блюда.
     */
    @Schema(description = "Состав блюда", example = "Картофель, масло, соль", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Состав блюда не должен быть пустым")
    @Size(max = 255, message = "Состав блюда не должен быть больше 255 символов")
    private final String composition;

    /**
     * Стоимость блюда.
     */
    @Schema(description = "Стоимость блюда", example = "320", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive(message = "Стоимость блюда должна быть положительным числом")
    @Digits(integer = 5, fraction = 2, message = "Стоимость блюда может содержать 5 целых и 2 десятичных знака")
    private final String cost;
}
