package ru.kaznacheev.restaurant.kitchenservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.restaurant.common.dto.response.KitchenDishResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;

import java.util.List;

/**
 * Маппер для преобразования блюд.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishMapper {

    /**
     * Преобразует {@link Dish} в {@link KitchenDishResponse}.
     *
     * @param dish Сущность блюда
     * @return {@link KitchenDishResponse} с информацией о блюде
     */
    KitchenDishResponse toKitchenDishResponse(Dish dish);

    /**
     * Преобразует {@link List} {@link Dish} в {@link List} {@link KitchenDishResponse}.
     *
     * @param dishes Список сущностей блюд
     * @return {@link List} {@link KitchenDishResponse} с информацией о блюде
     */
    List<KitchenDishResponse> toKitchenDishResponseList(List<Dish> dishes);

}
