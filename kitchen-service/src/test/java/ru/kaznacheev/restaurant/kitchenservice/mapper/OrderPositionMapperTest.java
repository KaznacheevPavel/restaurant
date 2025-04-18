package ru.kaznacheev.restaurant.kitchenservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPositionCompositeId;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderPositionMapperTest {

    private final OrderPositionMapper orderPositionMapper = Mappers.getMapper(OrderPositionMapper.class);

    @Test
    @DisplayName("Should map list of OrderPosition to list of OrderPositionResponse")
    void shouldMapToOrderPositionResponseList() {
        // Подготовка
        List<OrderPosition> orderPositions = List.of(
                OrderPosition.builder()
                        .id(new OrderPositionCompositeId(1L, 12L))
                        .dish(Dish.builder()
                                .id(12L)
                                .shortName("Борщ")
                                .build())
                        .amount(3L)
                        .build(),
                OrderPosition.builder()
                        .id(new OrderPositionCompositeId(1L, 16L))
                        .dish(Dish.builder()
                                .id(16L)
                                .shortName("Карбонара")
                                .build())
                        .amount(2L)
                        .build()
        );
        List<OrderPositionResponse> expected = List.of(
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

        // Действие
        List<OrderPositionResponse> actual = orderPositionMapper.toOrderPositionResponseList(orderPositions);

        // Проверка
        assertThat(actual).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }

}