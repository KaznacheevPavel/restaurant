package ru.kaznacheev.restaurant.waiterservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.restaurant.waiterservice.constraint.ValidEnum;
import ru.kaznacheev.restaurant.waiterservice.entity.Gender;

/**
 * DTO для запроса на создание нового официанта.
 */
@Schema(description = "Информация для создания нового официанта")
@AllArgsConstructor
@Getter
public class CreateWaiterRequest {

    /**
     * Имя официанта.
     */
    @Schema(description = "Имя официанта", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Имя официанта не должно быть пустым")
    @Size(min = 1, message = "Имя официанта не должно быть меньше 1 символа")
    @Size(max = 255, message = "Имя официанта не должно быть больше 255 символов")
    private final String name;

    /**
     * Пол официанта.
     */
    @Schema(description = "Пол официанта", examples = {"MALE", "FEMALE"}, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Пол официанта не должен быть пустым")
    @ValidEnum(enumClass = Gender.class, message = "Пол официанта должен быть MALE или FEMALE")
    private final String sex;

}
