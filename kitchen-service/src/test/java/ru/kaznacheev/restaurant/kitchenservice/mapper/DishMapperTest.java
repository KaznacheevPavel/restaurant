package ru.kaznacheev.restaurant.kitchenservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.kaznacheev.restaurant.common.dto.response.KitchenDishResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DishMapperTest {

    private final DishMapper dishMapper = Mappers.getMapper(DishMapper.class);

    @Test
    @DisplayName("Should map Dish to KitchenDishResponse")
    void shouldMapToKitchenDishResponse() {
        // Подготовка
        Dish dish = Dish.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(12L)
                .build();
        KitchenDishResponse excepted = KitchenDishResponse.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(12L)
                .build();

        // Действие
        KitchenDishResponse actual = dishMapper.toKitchenDishResponse(dish);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(excepted);
    }

    @Test
    @DisplayName("Should return null when map Dish to KitchenDishResponse if dish is null")
    void shouldReturnNullWhenMapToKitchenDishResponse() {
        // Действие
        KitchenDishResponse actual = dishMapper.toKitchenDishResponse(null);

        // Проверка
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Should map list of Dish to list of KitchenDishResponse")
    void shouldMapToKitchenDishResponseList() {
        // Подготовка
        List<Dish> dishes = List.of(
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
                        .balance(2L)
                        .build()
        );
        List<KitchenDishResponse> expected = List.of(
                KitchenDishResponse.builder()
                        .id(1L)
                        .shortName("Борщ")
                        .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                        .balance(12L)
                        .build(),
                KitchenDishResponse.builder()
                        .id(2L)
                        .shortName("Карбонара")
                        .composition("Паста, панчетта, сыр, яйца")
                        .balance(2L)
                        .build()
        );

        // Действие
        List<KitchenDishResponse> actual = dishMapper.toKitchenDishResponseList(dishes);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return null when map list of Dish to list of KitchenDishResponse if dishes is null")
    void shouldReturnNullWhenMapToKitchenDishResponseList() {
        // Действие
        List<KitchenDishResponse> actual = dishMapper.toKitchenDishResponseList(null);

        // Проверка
        assertThat(actual).isNull();
    }

}