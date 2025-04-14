package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.kitchenservice.mapper.OrderMapper;
import ru.kaznacheev.restaurant.kitchenservice.repository.OrderRepository;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderEventProducerService;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderPositionService;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderService;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Реализация интерфейса {@link OrderService}.
 */
@Service
@Validated
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final Clock clock;
    private final OrderPositionService orderPositionService;
    private final OrderEventProducerService orderEventProducerService;
    private final OrderMapper orderMapper;

    /**
     * {@inheritDoc}
     *
     * @param request {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional
    @Override
    public KitchenOrderResponse createOrder(@Valid CreateKitchenOrderRequest request) {
        Order order = Order.builder()
                .waiterOrderId(request.getWaiterOrderId())
                .status(OrderStatus.NEW)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        orderRepository.save(order);
        List<OrderPositionResponse> dishes = orderPositionService.addDishesToOrder(order, request.getDishes());
        order.setStatus(OrderStatus.IN_PROGRESS);
        return orderMapper.toKitchenOrderResponse(order, dishes);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NotFoundBaseException Если заказ не был найден
     */
    @Transactional(readOnly = true)
    @Override
    public KitchenOrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.ORDER_NOT_FOUND_BY_ID.format(id)));
        List<OrderPositionResponse> dishes = orderPositionService.getOrderPositions(order);
        return orderMapper.toKitchenOrderResponse(order, dishes);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<OrderShortInfoResponse> getAllOrders() {
        return orderMapper.toOrderShortInfoResponseList(orderRepository.findAll());
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NotFoundBaseException Если заказ не был найден
     */
    @Transactional(readOnly = true)
    @Override
    public OrderStatusResponse getOrderStatus(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.ORDER_NOT_FOUND_BY_ID.format(id)));
        return orderMapper.toOrderStatusResponse(order);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NotFoundBaseException Если заказ не был найден
     * @throws ConflictBaseException Если заказ не может быть завершен
     */
    @Transactional
    @Override
    public OrderStatusResponse completeOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.ORDER_NOT_FOUND_BY_ID.format(id)));
        if (!OrderStatus.IN_PROGRESS.equals(order.getStatus())) {
            throw new ConflictBaseException(ExceptionDetail.ORDER_COOK_EXCEPTION.format(OrderStatus.IN_PROGRESS));
        }
        order.setStatus(OrderStatus.COMPLETED);
        orderEventProducerService.sendOrderCompletedEvent(order.getWaiterOrderId());
        return orderMapper.toOrderStatusResponse(order);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NotFoundBaseException Если заказ не был найден
     * @throws ConflictBaseException Если заказ не может быть отклонен
     */
    @Transactional
    @Override
    public OrderStatusResponse rejectOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.ORDER_NOT_FOUND_BY_ID.format(id)));
        if (!OrderStatus.IN_PROGRESS.equals(order.getStatus())) {
            throw new ConflictBaseException(ExceptionDetail.ORDER_REJECT_EXCEPTION.format(OrderStatus.IN_PROGRESS));
        }
        order.setStatus(OrderStatus.REJECTED);
        orderEventProducerService.sendOrderRejectedEvent(order.getWaiterOrderId());
        return orderMapper.toOrderStatusResponse(order);
    }

}
