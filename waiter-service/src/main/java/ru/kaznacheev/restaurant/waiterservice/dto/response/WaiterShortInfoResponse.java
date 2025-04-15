package ru.kaznacheev.restaurant.waiterservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для ответа с краткой информацией об официанте.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WaiterShortInfoResponse {

    /**
     * Идентификатор официанта.
     */
    private Long id;

    /**
     * Имя официанта.
     */
    private String name;

}
