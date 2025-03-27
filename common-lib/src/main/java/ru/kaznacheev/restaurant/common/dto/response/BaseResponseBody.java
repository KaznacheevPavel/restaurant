package ru.kaznacheev.restaurant.common.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Базовый класс для тела ответа.
 */
@Getter
@SuperBuilder
public class BaseResponseBody {
    /**
     * Название результата операции {@link ResponseTitle}.
     */
    private final String title;

    /**
     * Статус код ответа {@link ResponseTitle}.
     */
    private final int status;

    /**
     * Детальное описание результата операции.
     */
    private final String detail;
}
