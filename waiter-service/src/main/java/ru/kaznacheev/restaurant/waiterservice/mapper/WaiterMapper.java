package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Маппер для работы с официантами.
 */
@Mapper
@Repository
public interface WaiterMapper {

    /**
     * Проверяет, существует ли официант с заданным идентификатором.
     *
     * @param id Идентификатор официанта
     * @return {@code true}, если официант существует, {@code false} в противном случае
     */
    boolean existsWaiterById(Long id);

}
