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
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.waiterservice.mapper.OrderPositionMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.OrderPositionRepository;
import ru.kaznacheev.restaurant.waiterservice.service.DishService;

import java.math.BigDecimal;
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
    @DisplayName("Should create order composition and return list of OrderPositionResponse")
    void shouldCreateOrderComposition() {
        // Подготовка
        Long orderId = 1L;
        Map<String, Long> composition = Map.of("Борщ с пампушками", 1L, "Солянка", 2L);
        List<DishResponse> foundedDishes = List.of(
                DishResponse.builder()
                        .id(1L)
                        .name("Борщ с пампушками")
                        .cost(new BigDecimal("100.00"))
                        .build(),
                DishResponse.builder()
                        .id(2L)
                        .name("Солянка")
                        .cost(new BigDecimal("50.00"))
                        .build()
        );
        List<OrderPosition> expectedOrderPositions = List.of(
                OrderPosition.builder()
                        .orderId(orderId)
                        .dishId(1L)
                        .amount(1L)
                        .build(),
                OrderPosition.builder()
                        .orderId(orderId)
                        .dishId(2L)
                        .amount(2L)
                        .build()
        );
        List<OrderPositionResponse> expected = List.of(
                OrderPositionResponse.builder()
                        .dishId(1L)
                        .name("Борщ с пампушками")
                        .amount(1L)
                        .build(),
                OrderPositionResponse.builder()
                        .dishId(2L)
                        .name("Солянка")
                        .amount(2L)
                        .build()
        );
        when(dishService.getAllDishesByNames(composition.keySet().stream().toList())).thenReturn(foundedDishes);
        when(orderPositionMapper.toOrderPositionResponseList(composition, foundedDishes)).thenReturn(expected);

        // Действие
        List<OrderPositionResponse> actual = orderPositionService.createOrderComposition(orderId, composition);

        // Проверка
        verify(dishService).getAllDishesByNames(composition.keySet().stream().toList());
        ArgumentCaptor<List<OrderPosition>> positionsCaptor = ArgumentCaptor.forClass(List.class);
        verify(orderPositionRepository).saveAll(positionsCaptor.capture());
        assertThat(positionsCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedOrderPositions);
        verify(orderPositionMapper).toOrderPositionResponseList(composition, foundedDishes);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should throw exception when getting by id if dish not exists")
    void shouldThrowExceptionWhenDishNotFound() {
        // Подготовка
        Long orderId = 1L;
        Map<String, Long> composition = Map.of("Борщ с пампушками", 1L, "Солянка", 2L);
        String expectedDetail = "Некорректный состав заказа";
        when(dishService.getAllDishesByNames(composition.keySet().stream().toList())).thenReturn(List.of());

        // Действие
        Executable executable = () -> orderPositionService.createOrderComposition(orderId, composition);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

}