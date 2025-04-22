package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.kitchenservice.mapper.OrderMapper;
import ru.kaznacheev.restaurant.kitchenservice.repository.OrderRepository;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderEventProducerService;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderPositionService;

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
    private OrderPositionService orderPositionService;
    @Mock
    private OrderEventProducerService orderEventProducerService;
    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Should create order and return KitchenOrderResponse")
    void shouldCreateOrder() {
        // Подготовка
        when(clock.instant()).thenReturn(Instant.parse("2024-01-01T12:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        CreateKitchenOrderRequest request = CreateKitchenOrderRequest.builder()
                .waiterOrderId(12L)
                .dishes(Map.of(1L, 3L, 2L, 1L))
                .build();
        Order orderBeforeSave = Order.builder()
                .waiterOrderId(12L)
                .status(OrderStatus.NEW)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        Order orderAfterSave = Order.builder()
                .id(1L)
                .waiterOrderId(12L)
                .status(OrderStatus.IN_PROGRESS)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        List<OrderPositionResponse> expectedPositions = List.of();
        KitchenOrderResponse expectedResponse = KitchenOrderResponse.builder()
                .id(1L)
                .waiterOrderId(12L)
                .status(OrderStatus.IN_PROGRESS.name())
                .createdAt(OffsetDateTime.now(clock))
                .dishes(expectedPositions)
                .build();
        doAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        }).when(orderRepository).save(any(Order.class));
        when(orderPositionService.addDishesToOrder(any(Order.class), any(Map.class))).thenReturn(expectedPositions);
        when(orderMapper.toKitchenOrderResponse(any(Order.class), any(List.class))).thenReturn(expectedResponse);

        // Действие
        KitchenOrderResponse actualResponse = orderService.createOrder(request);

        // Проверка
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        assertThat(orderCaptor.getValue()).usingRecursiveComparison()
                .ignoringFields("id", "status").isEqualTo(orderBeforeSave);
        verify(orderMapper).toKitchenOrderResponse(orderCaptor.capture(), any());
        assertThat(orderCaptor.getValue()).usingRecursiveComparison().isEqualTo(orderAfterSave);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return KitchenOrderResponse when get order by id")
    void shouldReturnKitchenOrderResponse() {
        // Подготовка
        when(clock.instant()).thenReturn(Instant.parse("2024-01-01T12:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        Order expectedOrder = Order.builder()
                .id(1L)
                .waiterOrderId(12L)
                .status(OrderStatus.IN_PROGRESS)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        KitchenOrderResponse expectedResponse = KitchenOrderResponse.builder()
                .id(1L)
                .waiterOrderId(12L)
                .status(OrderStatus.IN_PROGRESS.name())
                .createdAt(OffsetDateTime.now(clock))
                .dishes(List.of())
                .build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));
        when(orderPositionService.getOrderPositions(expectedOrder)).thenReturn(List.of());
        when(orderMapper.toKitchenOrderResponse(expectedOrder, List.of())).thenReturn(expectedResponse);

        // Действие
        KitchenOrderResponse actualResponse = orderService.getOrderById(1L);

        // Проверка
        verify(orderRepository).findById(1L);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderPositionService).getOrderPositions(orderCaptor.capture());
        assertThat(orderCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedOrder);
        verify(orderMapper).toKitchenOrderResponse(orderCaptor.capture(), any());
        assertThat(orderCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedOrder);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should throw exception when order not found by id")
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
    @DisplayName("Should return list of OrderShortInfoResponse when get all orders")
    void shouldReturnOrderShortInfoResponseList() {
        // Подготовка
        when(clock.instant()).thenReturn(Instant.parse("2024-01-01T12:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        List<Order> expectedOrders = List.of(
                Order.builder()
                        .id(1L)
                        .waiterOrderId(12L)
                        .status(OrderStatus.IN_PROGRESS)
                        .createdAt(OffsetDateTime.now(clock))
                        .build(),
                Order.builder()
                        .id(2L)
                        .waiterOrderId(13L)
                        .status(OrderStatus.COMPLETED)
                        .createdAt(OffsetDateTime.now(clock))
                        .build()
        );
        List<OrderShortInfoResponse> expectedResponse = List.of(
                OrderShortInfoResponse.builder()
                        .id(1L)
                        .waiterOrderId(12L)
                        .status(OrderStatus.IN_PROGRESS)
                        .createdAt(OffsetDateTime.now(clock))
                        .build(),
                OrderShortInfoResponse.builder()
                        .id(2L)
                        .waiterOrderId(13L)
                        .status(OrderStatus.COMPLETED)
                        .createdAt(OffsetDateTime.now(clock))
                        .build()
        );
        when(orderRepository.findAll()).thenReturn(expectedOrders);
        when(orderMapper.toOrderShortInfoResponseList(expectedOrders)).thenReturn(expectedResponse);

        // Действие
        List<OrderShortInfoResponse> actualResponse = orderService.getAllOrders();

        // Проверка
        verify(orderRepository).findAll();
        ArgumentCaptor<List<Order>> ordersCaptor = ArgumentCaptor.forClass(List.class);
        verify(orderMapper).toOrderShortInfoResponseList(ordersCaptor.capture());
        assertThat(ordersCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedOrders);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return OrderStatusResponse when get order status by id")
    void shouldReturnOrderStatusResponse() {
        // Подготовка
        Order expectedOrder = Order.builder()
                .id(1L)
                .status(OrderStatus.IN_PROGRESS)
                .build();
        OrderStatusResponse expectedResponse = OrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.IN_PROGRESS.name())
                .build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));
        when(orderMapper.toOrderStatusResponse(expectedOrder)).thenReturn(expectedResponse);

        // Действие
        OrderStatusResponse actualResponse = orderService.getOrderStatus(1L);

        // Проверка
        verify(orderRepository).findById(1L);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderMapper).toOrderStatusResponse(orderCaptor.capture());
        assertThat(orderCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedOrder);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should throw exception when get order status if order not found by id")
    void shouldThrowExceptionWhenGetOrderStatus() {
        // Подготовка
        String expectedDetail = "Заказ с идентификатором 1 не найден";
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> orderService.getOrderStatus(1L);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(orderRepository).findById(1L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should complete order and return OrderStatusResponse")
    void shouldCompleteOrder() {
        // Подготовка
        Order expectedOrder = Order.builder()
                .id(1L)
                .status(OrderStatus.IN_PROGRESS)
                .build();
        OrderStatusResponse expectedResponse = OrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.COMPLETED.name())
                .build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));
        when(orderMapper.toOrderStatusResponse(expectedOrder)).thenReturn(expectedResponse);

        // Действие
        OrderStatusResponse actualResponse = orderService.completeOrder(1L);

        // Проверка
        verify(orderRepository).findById(1L);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderMapper).toOrderStatusResponse(orderCaptor.capture());
        assertThat(orderCaptor.getValue().getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(orderCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedOrder);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should throw exception when complete order if order not found by id")
    void shouldThrowExceptionIfOrderNotFoundWhenCompleteOrder() {
        // Подготовка
        String expectedDetail = "Заказ с идентификатором 1 не найден";
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> orderService.completeOrder(1L);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(orderRepository).findById(1L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should throw exception when complete order if order not to be completed")
    void shouldThrowExceptionIfOrderStatusMismatchWhenCompleteOrder() {
        // Подготовка
        Order expectedOrder = Order.builder()
                .id(1L)
                .status(OrderStatus.COMPLETED)
                .build();
        String expectedDetail = "Заказ 1 может быть приготовлен только в статусе IN_PROGRESS";
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));

        // Действие
        Executable executable = () -> orderService.completeOrder(1L);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        verify(orderRepository).findById(1L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should reject order and return OrderStatusResponse")
    void shouldRejectOrder() {
        // Подготовка
        Order expectedOrder = Order.builder()
                .id(1L)
                .status(OrderStatus.IN_PROGRESS)
                .build();
        OrderStatusResponse expectedResponse = OrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.REJECTED.name())
                .build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));
        when(orderMapper.toOrderStatusResponse(expectedOrder)).thenReturn(expectedResponse);

        // Действие
        OrderStatusResponse actualResponse = orderService.rejectOrder(1L);

        // Проверка
        verify(orderRepository).findById(1L);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderMapper).toOrderStatusResponse(orderCaptor.capture());
        assertThat(orderCaptor.getValue().getStatus()).isEqualTo(OrderStatus.REJECTED);
        assertThat(orderCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedOrder);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should throw exception when reject order if order not found by id")
    void shouldThrowExceptionIfOrderNotFoundWhenRejectOrder() {
        // Подготовка
        String expectedDetail = "Заказ с идентификатором 1 не найден";
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> orderService.rejectOrder(1L);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(orderRepository).findById(1L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should throw exception when reject order if order not to be rejected")
    void shouldThrowExceptionIfOrderStatusMismatchWhenRejectOrder() {
        // Подготовка
        Order expectedOrder = Order.builder()
                .id(1L)
                .status(OrderStatus.COMPLETED)
                .build();
        String expectedDetail = "Заказ 1 может быть отменен только в статусе IN_PROGRESS";
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));

        // Действие
        Executable executable = () -> orderService.rejectOrder(1L);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        verify(orderRepository).findById(1L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

}