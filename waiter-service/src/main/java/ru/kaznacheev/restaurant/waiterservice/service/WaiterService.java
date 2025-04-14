package ru.kaznacheev.restaurant.waiterservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateWaiterRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterShortInfoResponse;

import java.util.List;

/**
 * Интерфейс сервиса для работы с официантами.
 */
public interface WaiterService {

    /**
     * Создает нового официанта.
     *
     * @param request DTO с информацией о новом официанте
     * @return {@link WaiterResponse} с информацией о созданном официанте
     */
    WaiterResponse createWaiter(@Valid CreateWaiterRequest request);

    /**
     * Возвращает информацию об официанте по его идентификатору.
     *
     * @param id Идентификатор официанта
     * @return {@link WaiterResponse} с информацией об официанте
     */
    WaiterResponse getWaiterById(Long id);

    /**
     * Возвращает список с краткой информацией о всех официантах.
     *
     * @return {@link List} {@link WaiterShortInfoResponse} с краткой информацией об официанте
     */
    List<WaiterShortInfoResponse> getAllWaiters();

    /**
     * Проверяет, существует ли официант с заданным идентификатором.
     *
     * @param id Идентификатор официанта
     * @return {@code true} если официант с таким идентификатором существует, иначе {@code false}
     */
    boolean existsWaiterById(Long id);

}
