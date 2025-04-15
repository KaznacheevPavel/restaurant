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
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateDishRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;
import ru.kaznacheev.restaurant.waiterservice.service.DishService;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с блюдами.
 */
@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    /**
     * Создает новое блюдо.
     *
     * @param request DTO с информацией о новом блюде
     * @return {@link DishResponse} с информацией о созданном блюде
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DishResponse createDish(@RequestBody CreateDishRequest request) {
        return dishService.createDish(request);
    }

    /**
     * Возвращает информацию о блюде по его идентификатору.
     *
     * @param id Идентификатор блюда
     * @return {@link DishResponse} с информацией о блюде
     */
    @GetMapping("/{id}")
    public DishResponse getDish(@PathVariable Long id) {
        return dishService.getDishById(id);
    }

    /**
     * Возвращает список с информацией о всех блюдах.
     *
     * @return {@link List} {@link DishResponse} с информацией о блюде
     */
    @GetMapping
    public List<DishResponse> getAllDishes() {
        return dishService.getAllDishes();
    }

}
