package ru.kaznacheev.restaurant.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO для ответа с информацией о блюде на кухне.
 */
@Schema(description = "Информация о блюде")
@AllArgsConstructor
@Getter
@Builder
public class KitchenDishResponse {

    /**
     * Идентификатор блюда.
     */
    @Schema(description = "Идентификатор блюда", example = "2")
    private Long id;

    /**
     * Доступное количество порций.
     */
    @Schema(description = "Доступное количество порций", example = "25")
    private Long balance;

    /**
     * Сокращенное название блюда.
     */
    @Schema(description = "Сокращенное название блюда", example = "Борщ")
    private String shortName;

    /**
     * Состав блюда.
     */
    @Schema(description = "Состав блюда", example = "Свекла, капуста, картофель, морковь, лук, мясо, зелень")
    private String composition;

}
