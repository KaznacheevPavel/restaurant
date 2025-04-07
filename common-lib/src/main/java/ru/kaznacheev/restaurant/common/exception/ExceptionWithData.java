package ru.kaznacheev.restaurant.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Исключение, содержащее дополнительные данные.
 */
@Getter
public class ExceptionWithData extends BaseException {

    private final Map<String, ?> data;

    /**
     * Конструктор исключения.
     *
     * @param title Название ошибки.
     * @param status Статус код ответа.
     * @param detail Детальное описание ошибки.
     * @param data Дополнительные данные
     */
    public ExceptionWithData(String title, HttpStatus status, String detail, Map<String, ?> data) {
        super(title, status, detail);
        this.data = data;
    }

}
