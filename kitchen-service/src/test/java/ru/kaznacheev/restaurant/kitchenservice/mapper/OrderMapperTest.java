package ru.kaznacheev.restaurant.kitchenservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Test
    @DisplayName("Should map Order and list of OrderPositionResponse to KitchenOrderResponse")
    void shouldMapToKitchenOrderResponse() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T12:00:00Z"), ZoneId.systemDefault());
        Order order = createOrder(clock);
        List<OrderPositionResponse> positions = createOrderPositionResponses();
        KitchenOrderResponse excepted = createKitchenOrderResponse(clock, positions);

        // Действие
        KitchenOrderResponse actual = orderMapper.toKitchenOrderResponse(order, positions);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(excepted);
    }

    @Test
    @DisplayName("Should return null map Order and list of OrderPositionResponse to KitchenOrderResponse if args is null")
    void shouldReturnNullWhenMapToKitchenOrderResponse() {
        // Действие
        KitchenOrderResponse actual = orderMapper.toKitchenOrderResponse(null, null);

        // Проверка
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Should map list of Order to List of OrderShortInfoResponse")
    void shouldMapToOrderShortInfoResponseList() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T12:00:00Z"), ZoneId.systemDefault());
        List<Order> orders = createOrders(clock);
        List<OrderShortInfoResponse> expected = createOrderShortInfoResponses(clock);

        // Действие
        List<OrderShortInfoResponse> actual = orderMapper.toOrderShortInfoResponseList(orders);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should map list of Order to List of OrderShortInfoResponse if orders is null")
    void shouldReturnNullWhenMapToOrderShortInfoResponseList() {
        // Действие
        List<OrderShortInfoResponse> actual = orderMapper.toOrderShortInfoResponseList(null);

        // Проверка
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Should map Order to OrderShortInfoResponse")
    void shouldMapToOrderStatusResponse() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T12:00:00Z"), ZoneId.systemDefault());
        Order order = createOrder(clock);
        OrderStatusResponse expected = OrderStatusResponse.builder()
                .id(11L)
                .status(OrderStatus.COMPLETED.name())
                .build();

        // Действие
        OrderStatusResponse actual = orderMapper.toOrderStatusResponse(order);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return null when map Order to OrderShortInfoResponse")
    void shouldReturnNullWhenMapToOrderStatusResponse() {
        // Действие
        OrderStatusResponse actual = orderMapper.toOrderStatusResponse(null);

        // Проверка
        assertThat(actual).isNull();
    }

    private Order createOrder(Clock clock) {
        return Order.builder()
                .id(11L)
                .waiterOrderId(12L)
                .status(OrderStatus.COMPLETED)
                .createdAt(OffsetDateTime.now(clock))
                .build();
    }

    private List<OrderPositionResponse> createOrderPositionResponses() {
        return List.of(
                OrderPositionResponse.builder()
                        .dishId(1L)
                        .name("Борщ")
                        .amount(3L)
                        .build(),
                OrderPositionResponse.builder()
                        .dishId(2L)
                        .name("Карбонара")
                        .amount(2L)
                        .build()
        );
    }

    private KitchenOrderResponse createKitchenOrderResponse(Clock clock, List<OrderPositionResponse> positions) {
        return KitchenOrderResponse.builder()
                .id(11L)
                .waiterOrderId(12L)
                .status(OrderStatus.COMPLETED.name())
                .createdAt(OffsetDateTime.now(clock))
                .dishes(positions)
                .build();
    }

    private List<Order> createOrders(Clock clock) {
        return List.of(
                Order.builder()
                        .id(1L)
                        .waiterOrderId(1L)
                        .status(OrderStatus.COMPLETED)
                        .createdAt(OffsetDateTime.now(clock))
                        .build(),
                Order.builder()
                        .id(2L)
                        .waiterOrderId(2L)
                        .status(OrderStatus.REJECTED)
                        .createdAt(OffsetDateTime.now(clock))
                        .build()
        );
    }

    private List<OrderShortInfoResponse> createOrderShortInfoResponses(Clock clock) {
        return List.of(
                OrderShortInfoResponse.builder()
                        .id(1L)
                        .waiterOrderId(1L)
                        .status(OrderStatus.COMPLETED)
                        .createdAt(OffsetDateTime.now(clock))
                        .build(),
                OrderShortInfoResponse.builder()
                        .id(2L)
                        .waiterOrderId(2L)
                        .status(OrderStatus.REJECTED)
                        .createdAt(OffsetDateTime.now(clock))
                        .build()
        );
    }

}