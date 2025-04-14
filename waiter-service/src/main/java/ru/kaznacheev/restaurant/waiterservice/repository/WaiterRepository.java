package ru.kaznacheev.restaurant.waiterservice.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Waiter;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с официантами.
 */
@Mapper
@Repository
public interface WaiterRepository {

    /**
     * Сохраняет официанта.
     *
     * @param waiter Сущность официанта
     */
    void save(@Param("waiter") Waiter waiter);

    /**
     * Возвращает информацию об официанте по его идентификатору.
     *
     * @param id Идентификатор официанта
     * @return {@link Optional} {@link WaiterResponse} с информацией об официанте
     */
    Optional<WaiterResponse> findById(@Param("id") Long id);

    /**
     * Возвращает список с краткой информацией о всех официантах.
     *
     * @return {@link List} {@link WaiterShortInfoResponse} с краткой информацией об официанте
     */
    List<WaiterShortInfoResponse> findAll();

    /**
     * Проверяет, существует ли официант с заданным идентификатором.
     *
     * @param id Идентификатор официанта
     * @return {@code true} если официант с таким идентификатором существует, иначе {@code false}
     */
    boolean existsById(@Param("id") Long id);

}
