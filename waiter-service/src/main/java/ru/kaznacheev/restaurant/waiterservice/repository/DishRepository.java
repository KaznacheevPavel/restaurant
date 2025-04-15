package ru.kaznacheev.restaurant.waiterservice.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с блюдами.
 */
@Mapper
@Repository
public interface DishRepository {

    /**
     * Сохраняет блюдо.
     *
     * @param dish Сущность блюда
     */
    void save(@Param("dish") Dish dish);

    /**
     * Возвращает информацию о блюде по его идентификатору.
     *
     * @param id Идентификатор блюда
     * @return {@link Optional} {@link DishResponse} с информацией о блюде
     */
    Optional<DishResponse> findById(@Param("id") Long id);

    /**
     * Возвращает список с информацией о всех блюдах.
     *
     * @return {@link List} {@link DishResponse} с информацией о блюде
     */
    List<DishResponse> findAll();

    /**
     * Возвращает список с информацией о блюдах по переданным названиям.
     *
     * @param names Названия блюд
     * @return {@link List} {@link DishResponse} с информацией о блюде
     */
    List<DishResponse> findAllByNames(@Param("names") List<String> names);

    /**
     * Проверяет, существует ли блюдо с заданным названием.
     *
     * @param name Название блюда
     * @return {@code true} если блюдо с таким названием существует, иначе {@code false}
     */
    boolean existsByName(@Param("name") String name);

}
