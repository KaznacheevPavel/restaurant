package ru.kaznacheev.restaurant.waiterservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.restaurant.common.dto.response.BaseResponseBody;
import ru.kaznacheev.restaurant.common.dto.response.ResponseBodyWithData;
import ru.kaznacheev.restaurant.waiterservice.exception.WaiterNotFoundException;

/**
 * Контроллер для обработки исключений.
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    /**
     * Обрабатывает {@link WaiterNotFoundException}.
     *
     * @param e Исключение
     * @return {@link BaseResponseBody} с информацией о возникшем исключении
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WaiterNotFoundException.class)
    public BaseResponseBody handleDishNotFoundException(WaiterNotFoundException e) {
        return ResponseBodyWithData.builder()
                .title(e.getTitle())
                .status(e.getStatus())
                .detail(e.getDetail())
                .build();
    }

}
