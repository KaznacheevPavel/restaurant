package ru.kaznacheev.restaurant.kitchenservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.kaznacheev.restaurant.common.dto.response.ExceptionResponse;
import ru.kaznacheev.restaurant.common.dto.response.KitchenDishResponse;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.common.exception.ExceptionTitle;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;
import ru.kaznacheev.restaurant.kitchenservice.repository.DishRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class DishControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DishRepository dishRepository;

    @Test
    @DisplayName("Should return KitchenDishResponse when GET /api/v1/dishes/{id}")
    void shouldReturnKitchenDishResponse() throws Exception {
        // Подготовка
        Dish dish = Dish.builder()
                .id(5L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(0L)
                .build();
        dishRepository.save(dish);
        KitchenDishResponse expected = KitchenDishResponse.builder()
                .id(5L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(0L)
                .build();

        // Действие
        String jsonResponse = mockMvc.perform(get("/api/v1/dishes/{id}", dish.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        KitchenDishResponse actual = objectMapper.readValue(jsonResponse, KitchenDishResponse.class);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    @DisplayName("Should return DishNotFound when GET /api/v1/dishes/{id} if id not found")
    void shouldReturnDishNotFound() throws Exception {
        // Подготовка
        ExceptionResponse expected = ExceptionResponse.builder()
                .title(ExceptionTitle.NOT_FOUND.getTitle())
                .status(ExceptionTitle.NOT_FOUND.getStatus().value())
                .detail(ExceptionDetail.DISH_NOT_FOUND_BY_ID.format(555L))
                .build();

        // Действие
        String jsonResponse = mockMvc.perform(get("/api/v1/dishes/{id}", 555L))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ExceptionResponse actual = objectMapper.readValue(jsonResponse, ExceptionResponse.class);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}