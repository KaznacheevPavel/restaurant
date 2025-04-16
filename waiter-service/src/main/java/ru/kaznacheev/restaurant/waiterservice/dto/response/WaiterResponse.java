package ru.kaznacheev.restaurant.waiterservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.restaurant.waiterservice.entity.Gender;

import java.time.OffsetDateTime;

/**
 * DTO для ответа с информацией об официанте.
 */
@Schema(description = "Информация об официанте")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WaiterResponse {

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

    /**
     * Дата и время приема на работу.
     */
    @Schema(description = "Дата и время приема на работу", example = "2024-04-11T13:25:00.000Z")
    private OffsetDateTime employedAt;

    /**
     * Пол официанта из {@link Gender}.
     */
    @Schema(description = "Пол официанта", example = "MALE")
    private Gender sex;

}
