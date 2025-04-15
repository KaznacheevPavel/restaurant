package ru.kaznacheev.restaurant.waiterservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateDishRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;

import java.util.List;

/**
 * Интерфейс сервиса для работы с блюдами.
 */
public interface DishService {

    /**
     * Создает новое блюдо.
     *
     * @param request DTO с информацией о новом блюде
     * @return {@link DishResponse} с информацией о созданном блюде
     */
    DishResponse createDish(@Valid CreateDishRequest request);

    /**
     * Возвращает информацию о блюде по его идентификатору.
     *
     * @param id Идентификатор блюда
     * @return {@link DishResponse} с информацией о блюде
     */
    DishResponse getDishById(Long id);

    /**
     * Возвращает список с информацией о всех блюдах.
     *
     * @return {@link List} {@link DishResponse} с информацией о блюде
     */
    List<DishResponse> getAllDishes();

    /**
     * Возвращает список с информацией о блюдах по их названиям.
     *
     * @param names Названия блюд
     * @return {@link List} {@link DishResponse} с информацией о блюде
     */
    List<DishResponse> getAllDishesByNames(List<String> names);

}
