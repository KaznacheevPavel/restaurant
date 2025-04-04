package ru.kaznacheev.restaurant.waiterservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.exception.OrderNotFoundException;
import ru.kaznacheev.restaurant.waiterservice.dto.request.NewOrderRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderFullInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.waiterservice.exception.WaiterNotFoundException;
import ru.kaznacheev.restaurant.waiterservice.mapper.OrderFullInfoMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.OrderRepository;
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

    private final OrderRepository orderRepository;
    private final Clock clock;
    private final WaiterService waiterService;
    private final OrderPositionService orderPositionService;
    private final OrderFullInfoMapper orderFullInfoMapper;

    /**
     * {@inheritDoc}
     *
     * @param newOrderRequest {@inheritDoc}
     * @return {@inheritDoc}
     * @throws WaiterNotFoundException Если был передан неверный идентификатор официанта
     */
    @Transactional
    @Override
    public OrderFullInfoResponse createOrder(@Valid NewOrderRequest newOrderRequest) {
        if (!waiterService.existsWaiterById(newOrderRequest.getWaiterId())) {
            throw new WaiterNotFoundException(newOrderRequest.getWaiterId());
        }
        Order order = Order.builder()
                .waiterId(newOrderRequest.getWaiterId())
                .status(OrderStatus.ACCEPTED)
                .createdAt(OffsetDateTime.now(clock))
                .tableNumber(newOrderRequest.getTableNumber())
                .build();
        orderRepository.save(order);
        orderPositionService.addDishesToOrder(order.getId(), newOrderRequest.getDishes());
        return orderFullInfoMapper.toOrderFullInfoResponse(order, newOrderRequest.getDishes());
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws OrderNotFoundException Если заказ не найден
     */
    @Transactional(readOnly = true)
    @Override
    public OrderFullInfoResponse getOrderById(Long id) {
        Optional<OrderFullInfoResponse> order = orderRepository.getOrderById(id);
        return order.orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<OrderShortInfoResponse> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws OrderNotFoundException Если заказ не найден
     */
    @Transactional(readOnly = true)
    @Override
    public OrderStatusResponse getOrderStatusById(Long id) {
        Optional<OrderStatusResponse> orderStatus = orderRepository.getOrderStatusById(id);
        return orderStatus.orElseThrow(() -> new OrderNotFoundException(id));
    }
}
