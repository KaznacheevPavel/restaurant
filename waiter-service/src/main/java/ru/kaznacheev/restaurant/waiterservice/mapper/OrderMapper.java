package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * Маппер для работы с заказами.
 */
@Mapper
@Repository
public interface OrderMapper {

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
     * @return {@link Optional} {@link Order} Заказ
     */
    Optional<Order> getOrderById(@Param("id") Long id);

    /**
     * Возвращает список всех заказов.
     *
     * @return {@link List} {@link Order} Список всех заказов
     */
    List<Order> getAllOrders();

    /**
     * Возвращает статус заказа по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link Optional} {@link OrderStatus} Статус заказа
     */
    Optional<OrderStatus> getOrderStatusById(@Param("id") Long id);

}
