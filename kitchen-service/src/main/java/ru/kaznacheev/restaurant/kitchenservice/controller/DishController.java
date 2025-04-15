package ru.kaznacheev.restaurant.kitchenservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenDishResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.request.AddDishBalanceRequest;
import ru.kaznacheev.restaurant.kitchenservice.service.DishService;

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
     * @return {@link KitchenDishResponse} с информацией о созданном блюде
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenDishResponse createDish(@RequestBody CreateKitchenDishRequest request) {
        return dishService.createDish(request);
    }

    /**
     * Возвращает информацию о блюде по его идентификатору.
     *
     * @param id Идентификатор блюда
     * @return {@link KitchenDishResponse} с информацией о блюде
     */
    @GetMapping("/{id}")
    public KitchenDishResponse getDish(@PathVariable Long id) {
        return dishService.getDishById(id);
    }

    /**
     * Возвращает список с информацией о всех блюдах.
     *
     * @return {@link List} {@link KitchenDishResponse} с информацией о блюде
     */
    @GetMapping
    public List<KitchenDishResponse> getAllDishes() {
        return dishService.getAllDishes();
    }

    /**
     * Добавляет порции для блюда.
     *
     * @param id Идентификатор блюда
     * @param request DTO, с информацией о количестве порций
     * @return {@link KitchenDishResponse} с информацией о блюде
     */
    @PostMapping("/{id}/balance")
    public KitchenDishResponse addDishBalance(@PathVariable Long id, @RequestBody AddDishBalanceRequest request) {
        return dishService.addDishBalance(id, request);
    }

}
