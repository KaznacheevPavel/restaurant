package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPositionCompositeId;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.kitchenservice.mapper.OrderPositionMapper;
import ru.kaznacheev.restaurant.kitchenservice.repository.OrderPositionRepository;
import ru.kaznacheev.restaurant.kitchenservice.service.DishService;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderPositionServiceImplTest {

    @Mock
    private OrderPositionRepository orderPositionRepository;
    @Mock
    private DishService dishService;
    @Mock
    private OrderPositionMapper orderPositionMapper;

    @InjectMocks
    private OrderPositionServiceImpl orderPositionService;

    @Test
    @DisplayName("Should add dishes to order and return list of OrderPositionResponse")
    void addDishesToOrder() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T12:00:00Z"), ZoneId.systemDefault());
        Order order = Order.builder()
                .id(1L)
                .waiterOrderId(12L)
                .status(OrderStatus.IN_PROGRESS)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        Map<Long, Long> composition = Map.of(1L, 3L, 2L, 1L);
        List<Dish> expectedDishes = List.of(
                Dish.builder()
                        .id(1L)
                        .shortName("Борщ")
                        .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                        .balance(12L)
                        .build(),
                Dish.builder()
                        .id(2L)
                        .shortName("Карбонара")
                        .composition("Паста, панчетта, сыр, яйца")
                        .balance(10L)
                        .build()
        );
        List<OrderPosition> expectedOrderPositions = List.of(
                OrderPosition.builder()
                        .id(new OrderPositionCompositeId(1L, 1L))
                        .order(order)
                        .dish(expectedDishes.get(0))
                        .amount(3L)
                        .build(),
                OrderPosition.builder()
                        .id(new OrderPositionCompositeId(1L, 2L))
                        .order(order)
                        .dish(expectedDishes.get(1))
                        .amount(1L)
                        .build()
        );
        List<OrderPositionResponse> expectedResponse = List.of(
                OrderPositionResponse.builder()
                        .dishId(1L)
                        .name("Борщ")
                        .amount(3L)
                        .build(),
                OrderPositionResponse.builder()
                        .dishId(2L)
                        .name("Карбонара")
                        .amount(1L)
                        .build()
        );
        when(dishService.getAllDishesByIds(composition.keySet())).thenReturn(expectedDishes);
        when(orderPositionMapper.toOrderPositionResponseList(expectedOrderPositions)).thenReturn(expectedResponse);

        // Действие
        List<OrderPositionResponse> actualResponse = orderPositionService.addDishesToOrder(order, composition);

        // Проверка
        ArgumentCaptor<List<OrderPosition>> positionsCaptor = ArgumentCaptor.forClass(List.class);
        verify(orderPositionRepository).saveAll(positionsCaptor.capture());
        assertThat(positionsCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedOrderPositions);
        assertThat(order.getOrderPositions()).usingRecursiveComparison().isEqualTo(expectedOrderPositions);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should throw Exception when not all dishes found")
    void shouldThrowExceptionWhenNotAllDishesFound() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T12:00:00Z"), ZoneId.systemDefault());
        Order order = Order.builder()
                .id(1L)
                .waiterOrderId(12L)
                .status(OrderStatus.IN_PROGRESS)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        Map<Long, Long> composition = Map.of(1L, 3L, 2L, 1L);
        String expectedDetail = "Некорректный состав заказа";
        when(dishService.getAllDishesByIds(composition.keySet())).thenReturn(List.of());

        // Действие
        Executable executable = () -> orderPositionService.addDishesToOrder(order, composition);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should throw Exception when not all dishes sufficient")
    void shouldThrowExceptionWhenNotAllDishesSufficient() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T12:00:00Z"), ZoneId.systemDefault());
        Order order = Order.builder()
                .id(1L)
                .waiterOrderId(12L)
                .status(OrderStatus.IN_PROGRESS)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        Map<Long, Long> composition = Map.of(1L, 55L, 2L, 1L);
        List<Dish> expectedDishes = List.of(
                Dish.builder()
                        .id(1L)
                        .shortName("Борщ")
                        .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                        .balance(12L)
                        .build(),
                Dish.builder()
                        .id(2L)
                        .shortName("Карбонара")
                        .composition("Паста, панчетта, сыр, яйца")
                        .balance(10L)
                        .build()
        );
        String expectedDetail = "Некорректный состав заказа";
        when(dishService.getAllDishesByIds(composition.keySet())).thenReturn(expectedDishes);

        // Действие
        Executable executable = () -> orderPositionService.addDishesToOrder(order, composition);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should return list of OrderPositionResponse")
    void shouldReturnOrderPositionResponseList() {
        // Подготовка
        Order order = Order.builder()
                .id(1L)
                .build();
        List<OrderPosition> expectedOrderPositions = List.of(
                OrderPosition.builder()
                        .id(new OrderPositionCompositeId(1L, 1L))
                        .amount(3L)
                        .build(),
                OrderPosition.builder()
                        .id(new OrderPositionCompositeId(1L, 2L))
                        .amount(1L)
                        .build()
        );
        List<OrderPositionResponse> expectedResponse = List.of(
                OrderPositionResponse.builder()
                        .dishId(1L)
                        .amount(3L)
                        .build(),
                OrderPositionResponse.builder()
                        .dishId(2L)
                        .amount(1L)
                        .build()
        );
        when(orderPositionRepository.findAllWithDishesByOrderId(1L)).thenReturn(expectedOrderPositions);
        when(orderPositionMapper.toOrderPositionResponseList(expectedOrderPositions)).thenReturn(expectedResponse);

        // Действие
        List<OrderPositionResponse> actualResponse = orderPositionService.getOrderPositions(order);

        // Проверка
        verify(orderPositionRepository).findAllWithDishesByOrderId(1L);
        ArgumentCaptor<List<OrderPosition>> positionsCaptor = ArgumentCaptor.forClass(List.class);
        verify(orderPositionMapper).toOrderPositionResponseList(positionsCaptor.capture());
        assertThat(positionsCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedOrderPositions);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

}