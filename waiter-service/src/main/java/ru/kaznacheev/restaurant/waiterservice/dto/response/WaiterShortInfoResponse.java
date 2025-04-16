package ru.kaznacheev.restaurant.waiterservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для ответа с краткой информацией об официанте.
 */
@Schema(description = "Краткая информация об официанте")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WaiterShortInfoResponse {

    /**
     * Идентификатор официанта.
     */
    @Schema(description = "Идентификатор официанта", example = "1")
    private Long id;

    /**
     * Имя официанта.
     */
    @Schema(description = "Имя официанта", example = "Иван")
    private String name;

}
