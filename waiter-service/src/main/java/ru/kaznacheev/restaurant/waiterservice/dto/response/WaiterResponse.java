package ru.kaznacheev.restaurant.waiterservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.restaurant.waiterservice.entity.Gender;

import java.time.OffsetDateTime;

/**
 * DTO для ответа с информацией об официанте.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WaiterResponse {

    /**
     * Идентификатор официанта.
     */
    private Long id;

    /**
     * Имя официанта.
     */
    private String name;

    /**
     * Дата и время приема на работу.
     */
    private OffsetDateTime employedAt;

    /**
     * Пол официанта из {@link Gender}.
     */
    private Gender sex;

}
