package ru.kaznacheev.restaurant.kitchenservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO для запроса на добавление порций для блюда.
 */
@Schema(description = "Информация о количестве порций для добавления к блюду")
@AllArgsConstructor
@Getter
public class AddDishBalanceRequest {

    /**
     * Количество порций.
     */
    @Schema(description = "Добавляемое количество порций", example = "5", requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "1")
    @Positive(message = "Количество порций должно быть положительным числом")
    private final Long balance;

}
