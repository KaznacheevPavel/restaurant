package ru.kaznacheev.restaurant.waiterservice.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.common.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.repository.SimpleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация репозитория для хранения заказов в HashMap.
 */
@Repository
@Slf4j
public class OrderHashMapRepository implements SimpleRepository<Integer, Order> {

    private static int ORDER_COUNT;
    private final Map<Integer, Order> orders = new HashMap<>();

    /**
     * Сохраняет заказ.
     *
     * @param order Заказ, который нужно сохранить
     */
    @Override
    public void save(Order order) {
        order.setId(++ORDER_COUNT);
        log.debug("Saving order with id: {}", order.getId());
        orders.put(order.getId(), order);
    }

    /**
     * Возвращает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link Optional} с заказом, если он существует, иначе {@link Optional#empty()}
     */
    @Override
    public Optional<Order> getOrderById(Integer id) {
        log.debug("Getting order with id: {}", id);
        if (orders.containsKey(id)) {
            return Optional.of(orders.get(id));
        }
        return Optional.empty();
    }

    /**
     * Возвращает список всех заказов.
     *
     * @return Список заказов
     */
    @Override
    public List<Order> getAllOrders() {
        log.debug("Getting all orders");
        return orders.values().stream().toList();
    }
}
