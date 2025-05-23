package ru.kaznacheev.restaurant.waiterservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateOrderRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.waiterservice.mapper.OrderMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.OrderRepository;
import ru.kaznacheev.restaurant.waiterservice.service.DishService;
import ru.kaznacheev.restaurant.waiterservice.service.KitchenCommunicationService;
import ru.kaznacheev.restaurant.waiterservice.service.OrderPositionService;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;
import ru.kaznacheev.restaurant.waiterservice.service.WaiterService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link OrderService}.
 */
@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final Clock clock;
    private final WaiterService waiterService;
    private final DishService dishService;
    private final OrderPositionService orderPositionService;
    private final KitchenCommunicationService kitchenCommunicationService;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderResponse createOrder(@Valid CreateOrderRequest request) {
        log.info("Создание заказа для официанта с id: {} и составом: {}", request.getWaiterId(), request.getDishes());
        if (!waiterService.existsWaiterById(request.getWaiterId())) {
            throw new NotFoundBaseException(ExceptionDetail.WAITER_NOT_FOUND_BY_ID.format(request.getWaiterId()));
        }
        Order order = Order.builder()
                .status(OrderStatus.NEW)
                .createdAt(OffsetDateTime.now(clock))
                .waiterId(request.getWaiterId())
                .tableNumber(request.getTableNumber())
                .build();
        orderRepository.save(order);
        List<OrderPositionResponse> composition =
                orderPositionService.createOrderComposition(order.getId(), request.getDishes());
        BigDecimal cost = calculateOrderCost(request.getDishes());
        sendOrderToKitchen(order.getId());
        orderRepository.changeOrderStatus(order.getId(), OrderStatus.IN_PROGRESS);
        log.info("Заказ для официанта с id: {} успешно создан, id заказа: {}, состав: {}",
                order.getWaiterId(), order.getId(), request.getDishes());
        return orderMapper.toOrderResponse(order, cost, composition);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponse getOrderById(Long id) {
        log.debug("Получения информации о заказе с id: {}", id);
        OrderResponse order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.ORDER_NOT_FOUND_BY_ID.format(id)));
        Map<String, Long> composition = order.getComposition().stream()
                .collect(Collectors.toMap(OrderPositionResponse::getName, OrderPositionResponse::getAmount));
        order.setCost(calculateOrderCost(composition));
        return order;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderShortInfoResponse> getAllOrders() {
        log.debug("Получение информации о всех заказах");
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderStatusResponse getOrderStatus(Long id) {
        log.debug("Получение информации о статусе заказа с id: {}", id);
        return orderRepository.getOrderStatusById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.ORDER_NOT_FOUND_BY_ID.format(id)));
    }

    @Override
    public void paidForOrder(Long id) {
        log.info("Оплата заказа с id: {}", id);
        OrderStatusResponse status = getOrderStatus(id);
        if (!OrderStatus.COOKED.name().equals(status.getStatus())) {
            throw new ConflictBaseException(ExceptionDetail.ORDER_PAYMENT_EXCEPTION.format(id, OrderStatus.COOKED));
        }
        orderRepository.changeOrderStatus(id, OrderStatus.COMPLETED);
        log.info("Заказ с id {} успешно оплачен", id);
    }

    @Override
    public void cookOrder(Long id) {
        log.info("Подтверждение приготовления заказа с id: {}", id);
        OrderStatusResponse status = getOrderStatus(id);
        if (!OrderStatus.IN_PROGRESS.name().equals(status.getStatus())) {
            throw new ConflictBaseException(ExceptionDetail.ORDER_COOK_EXCEPTION.format(id, OrderStatus.IN_PROGRESS));
        }
        orderRepository.changeOrderStatus(id, OrderStatus.COOKED);
        log.info("Заказ с id {} успешно приготовлен", id);
    }

    @Override
    public void rejectOrder(Long id) {
        log.info("Отклонение заказа с id: {}", id);
        OrderStatusResponse status = getOrderStatus(id);
        if (!OrderStatus.IN_PROGRESS.name().equals(status.getStatus())) {
            throw new ConflictBaseException(ExceptionDetail.ORDER_REJECT_EXCEPTION.format(id, OrderStatus.IN_PROGRESS));
        }
        orderRepository.changeOrderStatus(id, OrderStatus.REJECTED);
        log.info("Заказ с id {} успешно отклонен", id);
    }

    /**
     * Рассчитывает стоимость заказа.
     *
     * @param composition Состав заказа
     * @return {@link BigDecimal} со стоимостью заказа
     */
    private BigDecimal calculateOrderCost(Map<String, Long> composition) {
        List<DishResponse> dishes = dishService.getAllDishesByNames(composition.keySet().stream().toList());
        BigDecimal cost = BigDecimal.ZERO;
        for (DishResponse dish : dishes) {
            long amount = composition.get(dish.getName());
            cost = cost.add(dish.getCost().multiply(BigDecimal.valueOf(amount)));
        }
        return cost;
    }

    /**
     * Создает запрос с новым заказом и отправляет на кухню.
     *
     * @param id Идентификатор заказа
     */
    private void sendOrderToKitchen(Long id) {
        OrderResponse order = getOrderById(id);
        CreateKitchenOrderRequest request = CreateKitchenOrderRequest.builder()
                .waiterOrderId(id)
                .dishes(order.getComposition().stream()
                        .collect(Collectors.toMap(OrderPositionResponse::getDishId, OrderPositionResponse::getAmount)))
                .build();
        kitchenCommunicationService.sendOrderToKitchen(request);
    }

}
