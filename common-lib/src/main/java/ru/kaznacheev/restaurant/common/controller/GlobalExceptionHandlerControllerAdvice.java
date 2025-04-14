package ru.kaznacheev.restaurant.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.restaurant.common.dto.exception.BaseExceptionResponseBody;
import ru.kaznacheev.restaurant.common.dto.exception.ExceptionResponseBodyWithData;
import ru.kaznacheev.restaurant.common.dto.exception.ResponseDetailMessages;
import ru.kaznacheev.restaurant.common.dto.exception.ResponseTitle;
import ru.kaznacheev.restaurant.common.exception.DishNotFoundException;
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
     * @return {@link ExceptionResponseBodyWithData} с информацией о возникшем исключении
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DishNotFoundException.class)
    public ExceptionResponseBodyWithData handleDishNotFoundException(DishNotFoundException e) {
        return ExceptionResponseBodyWithData.builder()
                .title(e.getTitle())
                .status(e.getStatus().value())
                .detail(e.getDetail())
                .data(e.getData())
                .build();
    }

    /**
     * Обрабатывает {@link OrderNotFoundException}.
     *
     * @param e Исключение
     * @return {@link BaseExceptionResponseBody} с информацией о возникшем исключении
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public BaseExceptionResponseBody handleOrderNotFoundException(OrderNotFoundException e) {
        return BaseExceptionResponseBody.builder()
                .title(e.getTitle())
                .status(e.getStatus().value())
                .detail(e.getDetail())
                .build();
    }

    /**
     * Обрабатывает {@link ConstraintViolationException}.
     *
     * @param e Исключение
     * @return {@link ExceptionResponseBodyWithData} с информацией о возникшем исключении
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseBodyWithData handleConstraintViolationException(ConstraintViolationException e) {
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
        return ExceptionResponseBodyWithData.builder()
                .title(ResponseTitle.VALIDATION_ERROR.name())
                .status(ResponseTitle.VALIDATION_ERROR.getStatus().value())
                .detail(ResponseDetailMessages.VALIDATION_ERROR.getDetail())
                .data(invalidFields)
                .build();
    }

    /**
     * Обрабатывает {@link FeignException}.
     *
     * @param e Исключение
     * @return {@link ResponseEntity} с информацией о возникшем исключении
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignException(FeignException e) {
        String responseBody = e.contentUTF8();
        try {
            Object errorBody = new ObjectMapper().readValue(responseBody, Object.class);
            return ResponseEntity.status(e.status()).body(errorBody);
        } catch (Exception ex) {
            return ResponseEntity.status(e.status()).body(responseBody);
        }
    }

}
