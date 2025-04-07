package ru.kaznacheev.restaurant.kitchenservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.restaurant.common.dto.exception.ExceptionResponseBodyWithData;
import ru.kaznacheev.restaurant.kitchenservice.exception.InsufficientDishException;

/**
 * Контроллер для обработки исключений.
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    /**
     * Обрабатывает {@link InsufficientDishException}.
     *
     * @param e Исключение
     * @return {@link ExceptionResponseBodyWithData} с информацией о возникшем исключении
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InsufficientDishException.class)
    public ExceptionResponseBodyWithData handleDishNotFoundException(InsufficientDishException e) {
        return ExceptionResponseBodyWithData.builder()
                .title(e.getTitle())
                .status(e.getStatus().value())
                .detail(e.getDetail())
                .data(e.getData())
                .build();
    }

}
