package ru.kaznacheev.restaurant.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.restaurant.common.dto.response.BaseResponseBody;
import ru.kaznacheev.restaurant.common.exception.BaseException;

/**
 * Контроллер для обработки исключений.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerControllerAdvice {

    /**
     * Метод для обработки {@link BaseException} и его наследников.
     *
     * @param e Исключение
     * @return {@link BaseResponseBody} с информацией о возникшем исключении
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponseBody> handleBaseException(BaseException e) {
        log.warn("Exception: {}", e.getDetail());
        BaseResponseBody response = BaseResponseBody.builder()
                .title(e.getTitle())
                .status(e.getStatus())
                .detail(e.getDetail())
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
