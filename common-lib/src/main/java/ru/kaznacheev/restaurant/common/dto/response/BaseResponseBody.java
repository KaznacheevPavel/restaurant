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
     * Название результата операции из {@link ResponseTitle}.
     */
    private final String title;

    /**
     * Статус код ответа из {@link ResponseTitle}.
     */
    private final int status;

    /**
     * Детальное описание результата.
     */
    private final String detail;

}
