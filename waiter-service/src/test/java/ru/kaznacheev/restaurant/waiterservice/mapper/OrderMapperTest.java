package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {

    private final OrderMapper dishMapper = Mappers.getMapper(OrderMapper.class);

    @Test
    @DisplayName("Should map Order, cost, list of OrderPositionResponse to OrderResponse")
    void shouldMapToOrderResponse() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T12:00:00Z"), ZoneId.systemDefault());
        Order order = Order.builder()
                .id(1L)
                .waiterId(1L)
                .tableNumber("3a")
                .status(OrderStatus.IN_PROGRESS)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        BigDecimal cost = new BigDecimal("100.00");
        List<OrderPositionResponse> positions = List.of(
                OrderPositionResponse.builder()
                        .dishId(12L)
                        .name("Борщ")
                        .amount(3L)
                        .build(),
                OrderPositionResponse.builder()
                        .dishId(16L)
                        .name("Карбонара")
                        .amount(2L)
                        .build()
        );
        OrderResponse expected = OrderResponse.builder()
                .id(1L)
                .status(OrderStatus.IN_PROGRESS)
                .createdAt(OffsetDateTime.now(clock))
                .waiterId(1L)
                .tableNumber("3a")
                .cost(cost)
                .composition(positions)
                .build();

        // Действие
        OrderResponse actual = dishMapper.toOrderResponse(order, cost, positions);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return null when map Order, cost, list of OrderPositionResponse to OrderResponse if args is null")
    void shouldReturnNullWhenMapToOrderResponse() {
        // Действие
        OrderResponse actual = dishMapper.toOrderResponse(null, null, null);

        // Проверка
        assertThat(actual).isNull();
    }

}