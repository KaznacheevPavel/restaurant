package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;

/**
 * Маппер для преобразования блюд.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishMapper {

    /**
     * Преобразует {@link Dish} в {@link DishResponse}.
     *
     * @param dish Сущность блюда
     * @return {@link DishResponse} с информацией о блюде
     */
    DishResponse toDishResponse(Dish dish);

}
