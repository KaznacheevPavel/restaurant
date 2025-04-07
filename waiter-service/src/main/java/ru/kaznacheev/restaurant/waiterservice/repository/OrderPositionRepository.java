package ru.kaznacheev.restaurant.waiterservice.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderPosition;

import java.util.List;

/**
 * Репозиторий для работы с позициями заказа.
 */
@Mapper
@Repository
public interface OrderPositionRepository {

    /**
     * Сохраняет позиции заказа.
     *
     * @param orderPositions Позиции заказа
     */
    void saveAll(@Param("orderPositions") List<OrderPosition> orderPositions);

}
