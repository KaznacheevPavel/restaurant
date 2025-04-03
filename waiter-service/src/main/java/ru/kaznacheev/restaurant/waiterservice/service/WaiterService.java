package ru.kaznacheev.restaurant.waiterservice.service;

/**
 * Интерфейс сервиса для работы с официантами.
 */
public interface WaiterService {

    /**
     * Проверяет, существует ли официант с заданным идентификатором.
     *
     * @param id Идентификатор официанта
     * @return {@code true}, если официант существует, {@code false} в противном случае
     */
    boolean existsWaiterById(Long id);

}
