package ru.kaznacheev.restaurant.waiterservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO для ответа с информацией о блюде.
 */
@Schema(description = "Информация о блюде")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DishResponse {

    /**
     * Идентификатор блюда.
     */
    @Schema(description = "Идентификатор блюда", example = "3")
    private Long id;

    /**
     * Название блюда.
     */
    @Schema(description = "Название блюда", example = "Картофель по-деревенски")
    private String name;

    /**
     * Стоимость блюда.
     */
    @Schema(description = "Стоимость блюда", example = "320")
    private BigDecimal cost;

}
