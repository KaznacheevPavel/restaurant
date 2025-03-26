package ru.kaznacheev.restaurant.kitchenservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.BaseResponse;
import ru.kaznacheev.restaurant.kitchenservice.exception.BaseException;

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
     * @return {@link BaseResponse} с информацией о возникшем исключении
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse> handleBaseException(BaseException e) {
        log.warn("Exception: {}", e.getDetail());
        BaseResponse response = BaseResponse.builder()
                .title(e.getTitle())
                .status(e.getStatus().value())
                .detail(e.getDetail())
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
