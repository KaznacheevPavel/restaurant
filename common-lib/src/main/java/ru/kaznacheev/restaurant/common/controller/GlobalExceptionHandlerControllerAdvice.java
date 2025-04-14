package ru.kaznacheev.restaurant.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.restaurant.common.dto.response.ExceptionResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.common.exception.ExceptionTitle;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;

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
     * Обрабатывает {@link NotFoundBaseException}.
     *
     * @param e Исключение
     * @return {@link ExceptionResponse} с информацией о возникшем исключении
     */
    @ExceptionHandler(NotFoundBaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundBaseException(NotFoundBaseException e) {
        return ExceptionResponse.builder()
                .title(ExceptionTitle.NOT_FOUND.getTitle())
                .status(ExceptionTitle.NOT_FOUND.getStatus().value())
                .detail(e.getDetail())
                .data(e.getData())
                .build();
    }

    /**
     * Обрабатывает {@link ConflictBaseException}.
     *
     * @param e Исключение
     * @return {@link ExceptionResponse} с информацией о возникшем исключении
     */
    @ExceptionHandler(ConflictBaseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleConflictBaseException(ConflictBaseException e) {
        return ExceptionResponse.builder()
                .title(ExceptionTitle.CONFLICT.getTitle())
                .status(ExceptionTitle.CONFLICT.getStatus().value())
                .detail(e.getDetail())
                .data(e.getData())
                .build();
    }

    /**
     * Обрабатывает {@link ConstraintViolationException}.
     *
     * @param e Исключение
     * @return {@link ExceptionResponse} с информацией о возникшем исключении
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException e) {
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
        return ExceptionResponse.builder()
                .title(ExceptionTitle.VALIDATION_EXCEPTION.getTitle())
                .status(ExceptionTitle.VALIDATION_EXCEPTION.getStatus().value())
                .detail(ExceptionDetail.VALIDATION_EXCEPTION.getTemplate())
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
