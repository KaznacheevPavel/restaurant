package ru.kaznacheev.restaurant.waiterservice.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

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
     * @param order Сущность заказа
     */
    void save(@Param("order") Order order);

    /**
     * Возвращает информацию о заказе по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link Optional} {@link OrderResponse} с информацией о заказе
     */
    Optional<OrderResponse> findById(@Param("id") Long id);

    /**
     * Возвращает список с краткой информацией о всех заказах.
     *
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    List<OrderShortInfoResponse> findAll();

    /**
     * Возвращает информацию о статусе заказа по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link Optional} {@link OrderStatusResponse} с информацией о статусе заказа
     */
    Optional<OrderStatusResponse> getOrderStatusById(@Param("id") Long id);

    /**
     * Изменяет статус заказа.
     *
     * @param id Идентификатор заказа
     * @param status Новый статус заказа
     */
    void changeOrderStatus(@Param("id") Long id, @Param("status") OrderStatus status);

}
