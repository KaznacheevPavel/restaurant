package ru.kaznacheev.restaurant.kitchenservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenDishResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.request.AddDishBalanceRequest;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;

import java.util.List;

/**
 * Интерфейс сервиса для работы с блюдами.
 */
public interface DishService {

    /**
     * Создает новое блюдо.
     *
     * @param request DTO с информацией о новом блюде
     * @return {@link KitchenDishResponse} с информацией о созданном блюде
     */
    KitchenDishResponse createDish(@Valid CreateKitchenDishRequest request);

    /**
     * Возвращает информацию о блюде по его идентификатору.
     *
     * @param id Идентификатор блюда
     * @return {@link KitchenDishResponse} с информацией о блюде
     */
    KitchenDishResponse getDishById(Long id);

    /**
     * Возвращает список с информацией о всех блюдах.
     *
     * @return {@link List} {@link KitchenDishResponse} с информацией о блюде
     */
    List<KitchenDishResponse> getAllDishes();

    /**
     * Возвращает список блюд по идентификаторам.
     *
     * @param ids Идентификаторы блюд
     * @return {@link List} {@link Dish} с информацией о блюде
     */
    List<Dish> getAllDishesByIds(Iterable<Long> ids);

    /**
     * Добавляет порции для блюда.
     *
     * @param id Идентификатор блюда
     * @param request DTO с информацией о количестве порций
     * @return {@link KitchenDishResponse} с информацией о блюде
     */
    KitchenDishResponse addDishBalance(Long id, @Valid AddDishBalanceRequest request);

}
