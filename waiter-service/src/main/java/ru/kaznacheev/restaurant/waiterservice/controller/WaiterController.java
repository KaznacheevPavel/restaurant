package ru.kaznacheev.restaurant.waiterservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateWaiterRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.service.WaiterService;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с официантами.
 */
@RestController
@RequestMapping("/api/v1/waiters")
@RequiredArgsConstructor
public class WaiterController {

    private final WaiterService waiterService;

    /**
     * Создает нового официанта.
     *
     * @param request DTO с информацией о новом официанте
     * @return {@link WaiterResponse} с информацией о созданном официанте
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WaiterResponse createWaiter(@RequestBody CreateWaiterRequest request) {
        return waiterService.createWaiter(request);
    }

    /**
     * Возвращает информацию об официанте по его идентификатору.
     *
     * @param id Идентификатор официанта
     * @return {@link WaiterResponse} с информацией об официанте
     */
    @GetMapping("/{id}")
    public WaiterResponse getWaiter(@PathVariable Long id) {
        return waiterService.getWaiterById(id);
    }

    /**
     * Возвращает список с краткой информацией о всех официантах.
     *
     * @return {@link List} {@link WaiterShortInfoResponse} с краткой информацией об официанте
     */
    @GetMapping
    public List<WaiterShortInfoResponse> getAllWaiters() {
        return waiterService.getAllWaiters();
    }

}
