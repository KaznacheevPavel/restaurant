package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OrderPositionMapperTest {

    private final OrderPositionMapper orderPositionMapper = Mappers.getMapper(OrderPositionMapper.class);

    @Test
    @DisplayName("Should map composition and list of DishResponse to list of OrderPositionResponse")
    void shouldMapToOrderPositionResponseList() {
        // Подготовка
        Map<String, Long> composition = Map.of("Борщ", 3L, "Солянка", 2L);
        List<DishResponse> dishes = List.of(
                DishResponse.builder()
                        .id(1L)
                        .name("Борщ")
                        .cost(new BigDecimal("100.00"))
                        .build(),
                DishResponse.builder()
                        .id(2L)
                        .name("Солянка")
                        .cost(new BigDecimal("50.00"))
                        .build()
        );
        List<OrderPositionResponse> expected = List.of(
                OrderPositionResponse.builder()
                        .dishId(1L)
                        .name("Борщ")
                        .amount(3L)
                        .build(),
                OrderPositionResponse.builder()
                        .dishId(2L)
                        .name("Солянка")
                        .amount(2L)
                        .build()
        );

        // Действие
        List<OrderPositionResponse> actual = orderPositionMapper.toOrderPositionResponseList(composition, dishes);

        // Проверка
        assertThat(actual).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }

}