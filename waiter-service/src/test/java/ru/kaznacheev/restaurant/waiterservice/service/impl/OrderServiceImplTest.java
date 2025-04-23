package ru.kaznacheev.restaurant.waiterservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateOrderRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.waiterservice.mapper.OrderMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.OrderRepository;
import ru.kaznacheev.restaurant.waiterservice.service.DishService;
import ru.kaznacheev.restaurant.waiterservice.service.KitchenCommunicationService;
import ru.kaznacheev.restaurant.waiterservice.service.OrderPositionService;
import ru.kaznacheev.restaurant.waiterservice.service.WaiterService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private Clock clock;
    @Mock
    private WaiterService waiterService;
    @Mock
    private DishService dishService;
    @Mock
    private OrderPositionService orderPositionService;
    @Mock
    private KitchenCommunicationService kitchenCommunicationService;
    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Should create order and return OrderResponse")
    void shouldCreateOrder() {
        // Подготовка
        when(clock.instant()).thenReturn(Instant.parse("2024-01-01T12:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        CreateOrderRequest request = CreateOrderRequest.builder()
                .waiterId(16L)
                .tableNumber("3a")
                .dishes(Map.of("Борщ", 1L, "Солянка", 2L))
                .build();
        Order expectedOrder = Order.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .createdAt(OffsetDateTime.now(clock))
                .waiterId(16L)
                .tableNumber("3a")
                .build();
        List<OrderPositionResponse> expectedOrderPositions = List.of(
                OrderPositionResponse.builder()
                        .dishId(1L)
                        .name("Борщ")
                        .amount(1L)
                        .build(),
                OrderPositionResponse.builder()
                        .dishId(2L)
                        .name("Солянка")
                        .amount(2L)
                        .build()
        );
        OrderResponse expectedResponse = OrderResponse.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .createdAt(OffsetDateTime.now(clock))
                .waiterId(16L)
                .tableNumber("3a")
                .cost(new BigDecimal("100.00"))
                .composition(expectedOrderPositions)
                .build();

        doAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        }).when(orderRepository).save(any(Order.class));
        when(waiterService.existsWaiterById(16L)).thenReturn(true);
        when(orderPositionService.createOrderComposition(1L, request.getDishes())).thenReturn(expectedOrderPositions);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedResponse));
        when(orderMapper.toOrderResponse(any(Order.class), any(BigDecimal.class), any(List.class))).thenReturn(expectedResponse);

        // Действие
        OrderResponse actualResponse = orderService.createOrder(request);

        // Проверка
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        assertThat(orderArgumentCaptor.getValue()).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedOrder);
        verify(orderPositionService).createOrderComposition(1L, request.getDishes());
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should throw exception when creating order if waiter not found")
    void shouldThrowExceptionWhenWaiterNotFound() {
        // Подготовка
        CreateOrderRequest request = CreateOrderRequest.builder()
                .waiterId(16L)
                .build();
        String expectedDetail = "Официант с идентификатором 16 не найден";
        when(waiterService.existsWaiterById(16L)).thenReturn(false);

        // Действие
        Executable executable = () -> orderService.createOrder(request);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(waiterService).existsWaiterById(16L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should return order response when getting by id")
    void shouldReturnOrderResponse() {
        // Подготовка
        when(clock.instant()).thenReturn(Instant.parse("2024-01-01T12:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        OrderResponse expectedResponse = OrderResponse.builder()
                .id(1L)
                .status(OrderStatus.IN_PROGRESS)
                .createdAt(OffsetDateTime.now(clock))
                .waiterId(16L)
                .tableNumber("3a")
                .cost(new BigDecimal("100.00"))
                .composition(List.of(
                        OrderPositionResponse.builder()
                                .dishId(1L)
                                .name("Борщ")
                                .amount(1L)
                                .build(),
                        OrderPositionResponse.builder()
                                .dishId(2L)
                                .name("Солянка")
                                .amount(2L)
                                .build()
                ))
                .build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedResponse));

        // Действие
        OrderResponse actualResponse = orderService.getOrderById(1L);

        // Проверка
        verify(orderRepository).findById(1L);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should throw exception when getting order by id if order not found")
    void shouldThrowExceptionWhenOrderNotFound() {
        // Подготовка
        String expectedDetail = "Заказ с идентификатором 1 не найден";
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> orderService.getOrderById(1L);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(orderRepository).findById(1L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should return list of OrderShortInfoResponse when getting all orders")
    void shouldReturnOrderShortInfoResponseList() {
        // Подготовка
        when(clock.instant()).thenReturn(Instant.parse("2024-01-01T12:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        List<OrderShortInfoResponse> expectedResponse = List.of(
                OrderShortInfoResponse.builder()
                        .id(1L)
                        .status(OrderStatus.IN_PROGRESS)
                        .createdAt(OffsetDateTime.now(clock))
                        .build(),
                OrderShortInfoResponse.builder()
                        .id(2L)
                        .status(OrderStatus.COMPLETED)
                        .createdAt(OffsetDateTime.now(clock))
                        .build()
        );
        when(orderRepository.findAll()).thenReturn(expectedResponse);

        // Действие
        List<OrderShortInfoResponse> actualResponse = orderService.getAllOrders();

        // Проверка
        verify(orderRepository).findAll();
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return OrderStatusResponse when getting order status by id")
    void shouldReturnOrderStatusResponse() {
        // Подготовка
        OrderStatusResponse expected = OrderStatusResponse.builder()
                .id(1L)
                .status("IN_PROGRESS")
                .build();
        when(orderRepository.getOrderStatusById(1L)).thenReturn(Optional.of(expected));

        // Действие
        OrderStatusResponse actual = orderService.getOrderStatus(1L);

        // Проверка
        verify(orderRepository).getOrderStatusById(1L);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should throw exception when getting order status by id if order not found")
    void shouldThrowExceptionWhenOrderStatusNotFound() {
        // Подготовка
        String expectedDetail = "Заказ с идентификатором 1 не найден";
        when(orderRepository.getOrderStatusById(1L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> orderService.getOrderStatus(1L);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(orderRepository).getOrderStatusById(1L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should change order status to COMPLETED when paid for order")
    void shouldPaidForOrder() {
        // Подготовка
        OrderStatusResponse expectedOrderStatusResponse = OrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.COOKED.name())
                .build();
        when(orderRepository.getOrderStatusById(1L)).thenReturn(Optional.of(expectedOrderStatusResponse));

        // Действие
        orderService.paidForOrder(1L);

        // Проверка
        verify(orderRepository).changeOrderStatus(1L, OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("Should throw exception when paid for order if order status is not COOKED")
    void shouldThrowExceptionWhenPaidForOrder() {
        // Подготовка
        OrderStatusResponse expectedOrderStatusResponse = OrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.IN_PROGRESS.name())
                .build();
        String expectedDetail = "Заказ 1 может быть оплачен только в статусе COOKED";
        when(orderRepository.getOrderStatusById(1L)).thenReturn(Optional.of(expectedOrderStatusResponse));

        // Действие
        Executable executable = () -> orderService.paidForOrder(1L);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should change order status to COOKED when cooking order")
    void shouldCookOrder() {
        // Подготовка
        OrderStatusResponse expectedOrderStatusResponse = OrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.IN_PROGRESS.name())
                .build();
        when(orderRepository.getOrderStatusById(1L)).thenReturn(Optional.of(expectedOrderStatusResponse));

        // Действие
        orderService.cookOrder(1L);

        // Проверка
        verify(orderRepository).changeOrderStatus(1L, OrderStatus.COOKED);
    }

    @Test
    @DisplayName("Should throw exception when cooking order if order status is not IN_PROGRESS")
    void shouldThrowExceptionWhenCookOrder() {
        // Подготовка
        OrderStatusResponse expectedOrderStatusResponse = OrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.COMPLETED.name())
                .build();
        String expectedDetail = "Заказ 1 может быть приготовлен только в статусе IN_PROGRESS";
        when(orderRepository.getOrderStatusById(1L)).thenReturn(Optional.of(expectedOrderStatusResponse));

        // Действие
        Executable executable = () -> orderService.cookOrder(1L);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should change order status to REJECTED when rejecting order")
    void shouldRejectOrder() {
        // Подготовка
        OrderStatusResponse expectedOrderStatusResponse = OrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.IN_PROGRESS.name())
                .build();
        when(orderRepository.getOrderStatusById(1L)).thenReturn(Optional.of(expectedOrderStatusResponse));

        // Действие
        orderService.rejectOrder(1L);

        // Проверка
        verify(orderRepository).changeOrderStatus(1L, OrderStatus.REJECTED);
    }

    @Test
    @DisplayName("Should throw exception when rejecting order if order status is not IN_PROGRESS")
    void shouldThrowExceptionWhenRejectOrder() {
        // Подготовка
        OrderStatusResponse expectedOrderStatusResponse = OrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.COMPLETED.name())
                .build();
        String expectedDetail = "Заказ 1 может быть отменен только в статусе IN_PROGRESS";
        when(orderRepository.getOrderStatusById(1L)).thenReturn(Optional.of(expectedOrderStatusResponse));

        // Действие
        Executable executable = () -> orderService.rejectOrder(1L);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

}