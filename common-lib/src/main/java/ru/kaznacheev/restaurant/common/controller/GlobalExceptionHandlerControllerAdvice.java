package ru.kaznacheev.restaurant.common.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.restaurant.common.dto.response.BaseResponseBody;
import ru.kaznacheev.restaurant.common.dto.response.ResponseBodyWithData;
import ru.kaznacheev.restaurant.common.dto.response.ResponseDetailMessages;
import ru.kaznacheev.restaurant.common.dto.response.ResponseTitle;
import ru.kaznacheev.restaurant.common.exception.BaseException;
import ru.kaznacheev.restaurant.common.exception.DishNotFoundException;
import ru.kaznacheev.restaurant.common.exception.ExceptionWithData;
import ru.kaznacheev.restaurant.common.exception.OrderNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для обработки исключений.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerControllerAdvice {

    /**
     * Обрабатывает {@link DishNotFoundException}.
     *
     * @param e Исключение
     * @return {@link ResponseBodyWithData} с информацией о возникшем исключении
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DishNotFoundException.class)
    public ResponseBodyWithData handleDishNotFoundException(DishNotFoundException e) {
        return ResponseBodyWithData.builder()
                .title(e.getTitle())
                .status(e.getStatus())
                .detail(e.getDetail())
                .data(e.getData())
                .build();
    }

    /**
     * Обрабатывает {@link OrderNotFoundException}.
     *
     * @param e Исключение
     * @return {@link BaseResponseBody} с информацией о возникшем исключении
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public BaseResponseBody handleOrderNotFoundException(OrderNotFoundException e) {
        return BaseResponseBody.builder()
                .title(e.getTitle())
                .status(e.getStatus())
                .detail(e.getDetail())
                .build();
    }

    /**
     * Обрабатывает {@link ConstraintViolationException}.
     *
     * @param e Исключение
     * @return {@link ResponseBodyWithData} с информацией о возникшем исключении
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBodyWithData handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, List<String>> invalidFields = new HashMap<>();
        e.getConstraintViolations().forEach(constraintViolation -> {
            String fieldPath = constraintViolation.getPropertyPath().toString();
            String fieldName = fieldPath.substring(fieldPath.lastIndexOf(".") + 1);
            if (invalidFields.containsKey(fieldName)) {
                invalidFields.get(fieldName).add(constraintViolation.getMessage());
            } else {
                List<String> reasons = new ArrayList<>();
                reasons.add(constraintViolation.getMessage());
                invalidFields.put(fieldName, reasons);
            }
        });
        return ResponseBodyWithData.builder()
                .title(ResponseTitle.VALIDATION_ERROR.name())
                .status(ResponseTitle.VALIDATION_ERROR.getStatus())
                .detail(ResponseDetailMessages.VALIDATION_ERROR.getDetail())
                .data(invalidFields)
                .build();
    }

}
