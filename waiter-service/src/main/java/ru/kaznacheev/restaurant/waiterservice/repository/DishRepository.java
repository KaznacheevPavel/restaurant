package ru.kaznacheev.restaurant.waiterservice.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;

import java.util.List;

/**
 * Репозиторий для работы с блюдами.
 */
@Mapper
@Repository
public interface DishRepository {

    /**
     * Возвращает список блюд по названиям.
     *
     * @param dishTitles названия блюд
     * @return {@link List} {@link Dish} Список блюд
     */
    List<Dish> getAllDishesByTitles(@Param("dishTitles") List<String> dishTitles);

}
