package ru.kaznacheev.restaurant.waiterservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.restaurant.common.dto.exception.BaseExceptionResponseBody;
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
     * @return {@link BaseExceptionResponseBody} с информацией о возникшем исключении
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WaiterNotFoundException.class)
    public BaseExceptionResponseBody handleDishNotFoundException(WaiterNotFoundException e) {
        return BaseExceptionResponseBody.builder()
                .title(e.getTitle())
                .status(e.getStatus().value())
                .detail(e.getDetail())
                .build();
    }

}
