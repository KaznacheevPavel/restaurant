package ru.kaznacheev.restaurant.common.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Базовый класс для всех ответов.
 */
@Getter
@SuperBuilder
public class BaseResponse {
    private final String title;
    private final int status;
    private final String detail;
}
