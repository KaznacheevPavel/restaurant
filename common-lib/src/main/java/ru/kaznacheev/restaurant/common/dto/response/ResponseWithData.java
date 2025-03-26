package ru.kaznacheev.restaurant.common.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * Класс для ответов, содержащих дополнительные данные.
 */
@Getter
@SuperBuilder
public class ResponseWithData extends BaseResponse {
    private final Map<String, ?> data;
}
