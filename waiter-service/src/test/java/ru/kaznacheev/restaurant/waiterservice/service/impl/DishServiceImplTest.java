package ru.kaznacheev.restaurant.waiterservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateDishRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;
import ru.kaznacheev.restaurant.waiterservice.mapper.DishMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.DishRepository;
import ru.kaznacheev.restaurant.waiterservice.service.KitchenCommunicationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishServiceImplTest {

    @Mock
    private DishRepository dishRepository;
    @Mock
    private KitchenCommunicationService kitchenCommunicationService;
    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private DishServiceImpl dishService;

    @Test
    @DisplayName("Should create dish and return DishResponse")
    void shouldCreateDish() {
        // Подготовка
        CreateDishRequest request = CreateDishRequest.builder()
                .name("Борщ с пампушками")
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .cost("100.00")
                .build();
        Dish expectedDish = Dish.builder()
                .id(1L)
                .name("Борщ с пампушками")
                .cost(new BigDecimal("100.00"))
                .build();
        DishResponse expectedResponse = DishResponse.builder()
                .id(1L)
                .name("Борщ с пампушками")
                .cost(new BigDecimal("100.00"))
                .build();
        CreateKitchenDishRequest expectedKitchenDishRequest = CreateKitchenDishRequest.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .build();
        doAnswer(invocation -> {
            Dish dish = invocation.getArgument(0);
            dish.setId(1L);
            return dish;
        }).when(dishRepository).save(any(Dish.class));
        when(dishRepository.existsByName("Борщ с пампушками")).thenReturn(false);
        when(dishMapper.toDishResponse(any(Dish.class))).thenReturn(expectedResponse);

        // Действие
        DishResponse actualResponse = dishService.createDish(request);

        // Проверка
        ArgumentCaptor<Dish> dishCaptor = ArgumentCaptor.forClass(Dish.class);
        verify(dishRepository).save(dishCaptor.capture());
        assertThat(dishCaptor.getValue()).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(expectedDish);
        ArgumentCaptor<CreateKitchenDishRequest> createKitchenDishRequestCaptor =
                ArgumentCaptor.forClass(CreateKitchenDishRequest.class);
        verify(kitchenCommunicationService).sendDishToKitchen(createKitchenDishRequestCaptor.capture());
        assertThat(createKitchenDishRequestCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedKitchenDishRequest);
        verify(dishMapper).toDishResponse(dishCaptor.capture());
        assertThat(dishCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedDish);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should throw exception when creating dish if dish name already exists")
    void shouldReturnExceptionIfDishNameAlreadyExists() {
        // Подготовка
        CreateDishRequest request = CreateDishRequest.builder()
                .name("Борщ с пампушками")
                .build();
        String expectedDetail = "Блюдо с названием Борщ с пампушками уже существует";
        when(dishRepository.existsByName("Борщ с пампушками")).thenReturn(true);

        // Действие
        Executable executable = () -> dishService.createDish(request);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        verify(dishRepository).existsByName("Борщ с пампушками");
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should return dish response when getting by id")
    void shouldReturnDishResponse() {
        // Подготовка
        DishResponse expectedResponse = DishResponse.builder()
                .id(1L)
                .name("Борщ с пампушками")
                .cost(new BigDecimal("100.00"))
                .build();
        when(dishRepository.findById(1L)).thenReturn(Optional.of(expectedResponse));

        // Действие
        DishResponse actualResponse = dishService.getDishById(1L);

        // Проверка
        verify(dishRepository).findById(1L);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should throw exception when getting dish by id if dish not exists")
    void shouldReturnExceptionIfDishNotExists() {
        // Подготовка
        String expectedDetail = "Блюдо с идентификатором 5 не найдено";
        when(dishRepository.findById(5L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> dishService.getDishById(5L);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(dishRepository).findById(5L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should return list of DishResponse when getting all dishes")
    void shouldReturnDishResponseList() {
        // Подготовка
        List<DishResponse> expected = List.of(
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
        when(dishRepository.findAll()).thenReturn(expected);

        // Действие
        List<DishResponse> actual = dishService.getAllDishes();

        // Проверка
        verify(dishRepository).findAll();
        assertThat(actual).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return list of DishResponse when getting all dishes by names")
    void shouldReturnDishResponseListByNames() {
        // Подготовка
        List<String> names = List.of("Борщ с пампушками", "Солянка");
        List<DishResponse> expected = List.of(
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
        when(dishRepository.findAllByNames(names)).thenReturn(expected);

        // Действие
        List<DishResponse> actual = dishService.getAllDishesByNames(names);

        // Проверка
        verify(dishRepository).findAllByNames(names);
        assertThat(actual).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }

}