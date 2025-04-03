package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;

import java.util.List;

/**
 * Маппер для работы с блюдами.
 */
@Mapper
@Repository
public interface DishMapper {

    /**
     * Возвращает список блюд по названиям.
     *
     * @param dishTitles названия блюд
     * @return {@link List} {@link Dish} Список блюд
     */
    List<Dish> getAllDishesByTitles(@Param("dishTitles") List<String> dishTitles);

}
