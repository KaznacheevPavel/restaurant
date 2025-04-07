package ru.kaznacheev.restaurant.waiterservice.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderFullInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с заказами.
 */
@Mapper
@Repository
public interface OrderRepository {

    /**
     * Сохраняет заказ.
     *
     * @param order Заказ
     */
    void save(@Param("order") Order order);

    /**
     * Возвращает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link Optional} {@link OrderFullInfoResponse} с информацией о заказе
     */
    Optional<OrderFullInfoResponse> getOrderById(@Param("id") Long id);

    /**
     * Возвращает список всех заказов.
     *
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    List<OrderShortInfoResponse> getAllOrders();

    /**
     * Возвращает статус заказа по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link Optional} {@link OrderStatusResponse} со статусом заказа
     */
    Optional<OrderStatusResponse> getOrderStatusById(@Param("id") Long id);

}
