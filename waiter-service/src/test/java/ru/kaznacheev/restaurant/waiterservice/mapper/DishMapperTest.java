package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DishMapperTest {

    private final DishMapper dishMapper = Mappers.getMapper(DishMapper.class);

    @Test
    @DisplayName("Should map Dish to DishResponse")
    void shouldMapToDishResponse() {
        // Подготовка
        Dish dish = createDish();
        DishResponse expected = createDishResponse();

        // Действие
        DishResponse actual = dishMapper.toDishResponse(dish);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return null when map Dish to DishResponse if dish is null")
    void shouldReturnNullWhenMapToDishResponse() {
        // Действие
        DishResponse actual = dishMapper.toDishResponse(null);

        // Проверка
        assertThat(actual).isNull();
    }

    private Dish createDish() {
        return Dish.builder()
                .id(1L)
                .name("Борщ с пампушками")
                .cost(new BigDecimal("100.00"))
                .build();
    }

    private DishResponse createDishResponse() {
        return DishResponse.builder()
                .id(1L)
                .name("Борщ с пампушками")
                .cost(new BigDecimal("100.00"))
                .build();
    }

}