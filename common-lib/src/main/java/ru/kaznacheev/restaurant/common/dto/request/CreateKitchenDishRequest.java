package ru.kaznacheev.restaurant.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO для запроса на создание нового блюда на кухне.
 */
@Schema(description = "Информация о новом блюде")
@Getter
@Builder
public class CreateKitchenDishRequest {

    /**
     * Идентификатор блюда.
     */
    @Schema(description = "Идентификатор блюда", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Идентификатор блюда не должен быть пустым")
    @Min(value = 1, message = "Идентификатор блюда должен быть больше 0")
    private final Long id;

    /**
     * Сокращенное название блюда.
     */
    @Schema(description = "Сокращенное название блюда", example = "По-деревенски",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Сокращенное название блюда не должно быть пустым")
    @Size(max = 32, message = "Сокращенное название блюда не должно быть больше 32 символов")
    private final String shortName;

    /**
     * Состав блюда.
     */
    @Schema(description = "Состав блюда", example = "Картофель, масло, соль",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Состав блюда не должен быть пустым")
    @Size(max = 255, message = "Состав блюда не должен быть больше 255 символов")
    private final String composition;

}
