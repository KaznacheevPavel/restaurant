package ru.kaznacheev.restaurant.kitchenservice.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO для запроса на добавление порций для блюда.
 */
@AllArgsConstructor
@Getter
public class AddDishBalanceRequest {

    /**
     * Количество порций.
     */
    @Positive(message = "Количество порций должно быть положительным числом")
    private final Long balance;

}
