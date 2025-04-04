package ru.kaznacheev.restaurant.waiterservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.exception.OrderNotFoundException;
import ru.kaznacheev.restaurant.waiterservice.dto.NewOrderRequest;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.waiterservice.exception.WaiterNotFoundException;
import ru.kaznacheev.restaurant.waiterservice.mapper.OrderMapper;
import ru.kaznacheev.restaurant.waiterservice.service.OrderPositionService;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;
import ru.kaznacheev.restaurant.waiterservice.service.WaiterService;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса {@link OrderService}.
 */
@Service
@Validated
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final Clock clock;
    private final WaiterService waiterService;
    private final OrderPositionService orderPositionService;

    /**
     * {@inheritDoc}
     *
     * @param newOrderRequest {@inheritDoc}
     * @throws WaiterNotFoundException Если был передан неверный идентификатор официанта
     */
    @Transactional
    @Override
    public void createOrder(@Valid NewOrderRequest newOrderRequest) {
        if (!waiterService.existsWaiterById(newOrderRequest.getWaiterId())) {
            throw new WaiterNotFoundException(newOrderRequest.getWaiterId());
        }
        Order order = Order.builder()
                .waiterId(newOrderRequest.getWaiterId())
                .status(OrderStatus.ACCEPTED)
                .createdAt(OffsetDateTime.now(clock))
                .tableNumber(newOrderRequest.getTableNumber())
                .build();
        orderMapper.save(order);
        orderPositionService.addDishesToOrder(order.getId(), newOrderRequest.getDishes());
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws OrderNotFoundException Если заказ не найден
     */
    @Override
    public Order getOrderById(Long id) {
        Optional<Order> order = orderMapper.getOrderById(id);
        return order.orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws OrderNotFoundException Если заказ не найден
     */
    @Override
    public OrderStatus getOrderStatusById(Long id) {
        Optional<OrderStatus> orderStatus = orderMapper.getOrderStatusById(id);
        return orderStatus.orElseThrow(() -> new OrderNotFoundException(id));
    }
}
