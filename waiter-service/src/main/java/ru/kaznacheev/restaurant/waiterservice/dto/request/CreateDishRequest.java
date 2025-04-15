package ru.kaznacheev.restaurant.waiterservice.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO для запроса на создание нового блюда.
 */
@AllArgsConstructor
@Getter
public class CreateDishRequest {

    /**
     * Название блюда.
     */
    @NotBlank(message = "Название блюда не должно быть пустым")
    @Size(max = 64, message = "Название блюда не должно быть больше 64 символов")
    private final String name;

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

    /**
     * Стоимость блюда.
     */
    @Positive(message = "Стоимость блюда должна быть положительным числом")
    @Digits(integer = 5, fraction = 2, message = "Стоимость блюда может содержать 5 целых и 2 десятичных знака")
    private final String cost;
}
