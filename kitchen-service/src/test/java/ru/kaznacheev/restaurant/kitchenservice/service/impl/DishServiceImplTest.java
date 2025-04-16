package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenDishResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.kitchenservice.dto.request.AddDishBalanceRequest;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;
import ru.kaznacheev.restaurant.kitchenservice.mapper.DishMapper;
import ru.kaznacheev.restaurant.kitchenservice.repository.DishRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishServiceImplTest {

    @Mock
    private DishRepository dishRepository;
    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private DishServiceImpl dishService;

    @Test
    @DisplayName("Should create dish and return KitchenDishResponse")
    void shouldCreateDish() {
        // Подготовка
        CreateKitchenDishRequest request = CreateKitchenDishRequest.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .build();
        Dish exceptedDish = Dish.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(0L)
                .build();
        KitchenDishResponse exceptedResponse = KitchenDishResponse.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(0L)
                .build();
        when(dishRepository.existsByShortName("Борщ")).thenReturn(false);
        when(dishMapper.toKitchenDishResponse(exceptedDish)).thenReturn(exceptedResponse);

        // Действие
        KitchenDishResponse actualResponse = dishService.createDish(request);

        // Проверка
        ArgumentCaptor<Dish> dishCaptor = ArgumentCaptor.forClass(Dish.class);
        verify(dishRepository).save(dishCaptor.capture());
        assertThat(dishCaptor.getValue()).usingRecursiveComparison().isEqualTo(exceptedDish);
        verify(dishMapper).toKitchenDishResponse(dishCaptor.capture());
        assertThat(dishCaptor.getValue()).usingRecursiveComparison().isEqualTo(exceptedDish);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(exceptedResponse);
    }

    @Test
    @DisplayName("Should throw exception if dish short name already exists when create dish")
    void shouldThrowExceptionWhenDishNameAlreadyExists() {
        // Подготовка
        CreateKitchenDishRequest request = CreateKitchenDishRequest.builder()
                .shortName("Борщ")
                .build();
        String expectedDetail = "Блюдо с названием Борщ уже существует";
        when(dishRepository.existsByShortName("Борщ")).thenReturn(true);

        // Действие
        Executable executable = () -> dishService.createDish(request);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        verify(dishRepository).existsByShortName("Борщ");
        assertThat(actualException.getDetail()).isEqualTo("Блюдо с названием Борщ уже существует");
    }

    @Test
    @DisplayName("Should return KitchenDishResponse when get dish by id")
    void shouldReturnKitchenDishResponse() {
        // Подготовка
        Dish exceptedDish = Dish.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(0L)
                .build();
        KitchenDishResponse exceptedResponse = KitchenDishResponse.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(0L)
                .build();
        when(dishRepository.findById(1L)).thenReturn(Optional.of(exceptedDish));
        when(dishMapper.toKitchenDishResponse(exceptedDish)).thenReturn(exceptedResponse);

        // Действие
        KitchenDishResponse actualResponse = dishService.getDishById(1L);

        // Проверка
        verify(dishRepository).findById(1L);
        ArgumentCaptor<Dish> dishCaptor = ArgumentCaptor.forClass(Dish.class);
        verify(dishMapper).toKitchenDishResponse(dishCaptor.capture());
        assertThat(dishCaptor.getValue()).usingRecursiveComparison().isEqualTo(exceptedDish);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(exceptedResponse);
    }

    @Test
    @DisplayName("Should throw exception when dish not found by id")
    void shouldThrowExceptionWhenDishNotFound() {
        // Подготовка
        String expectedDetail = "Блюдо с идентификатором 1 не найдено";
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> dishService.getDishById(1L);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(dishRepository).findById(1L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should return list of KitchenDishResponse when get all dishes")
    void shouldReturnKitchenDishResponseList() {
        // Подготовка
        List<Dish> expectedDishes = List.of(
                Dish.builder()
                        .id(1L)
                        .shortName("Борщ")
                        .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                        .balance(0L)
                        .build(),
                Dish.builder()
                        .id(2L)
                        .shortName("Карбонара")
                        .composition("Паста, панчетта, сыр, яйца")
                        .balance(2L)
                        .build()
        );
        List<KitchenDishResponse> expectedResponse = List.of(
                KitchenDishResponse.builder()
                        .id(1L)
                        .shortName("Борщ")
                        .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                        .balance(0L)
                        .build(),
                KitchenDishResponse.builder()
                        .id(2L)
                        .shortName("Карбонара")
                        .composition("Паста, панчетта, сыр, яйца")
                        .balance(2L)
                        .build()
        );
        when(dishRepository.findAll()).thenReturn(expectedDishes);
        when(dishMapper.toKitchenDishResponseList(expectedDishes)).thenReturn(expectedResponse);

        // Действие
        List<KitchenDishResponse> actualResponse = dishService.getAllDishes();

        // Проверка
        verify(dishRepository).findAll();
        ArgumentCaptor<List<Dish>> dishesCaptor = ArgumentCaptor.forClass(List.class);
        verify(dishMapper).toKitchenDishResponseList(dishesCaptor.capture());
        assertThat(dishesCaptor.getValue()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedDishes);
        assertThat(actualResponse).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return list of Dish when get all dishes by ids")
    void shouldReturnDishList() {
        // Подготовка
        List<Long> ids = List.of(1L, 2L);
        List<Dish> expectedDishes = List.of(
                Dish.builder()
                        .id(1L)
                        .shortName("Борщ")
                        .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                        .balance(0L)
                        .build(),
                Dish.builder()
                        .id(2L)
                        .shortName("Карбонара")
                        .composition("Паста, панчетта, сыр, яйца")
                        .balance(2L)
                        .build()
        );
        when(dishRepository.findAllById(ids)).thenReturn(expectedDishes);

        // Действие
        List<Dish> actualDishes = dishService.getAllDishesByIds(ids);

        // Проверка
        verify(dishRepository).findAllById(ids);
        assertThat(actualDishes).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedDishes);
    }

    @Test
    @DisplayName("Should add dish balance and return KitchenDishResponse")
    void shouldAddDishBalance() {
        // Подготовка
        AddDishBalanceRequest request = new AddDishBalanceRequest(19L);
        Dish dishBeforeEdit = Dish.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(12L)
                .build();
        Dish dishAfterEdit = Dish.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(31L)
                .build();
        KitchenDishResponse exceptedResponse = KitchenDishResponse.builder()
                .id(1L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .balance(31L)
                .build();
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dishBeforeEdit));
        when(dishMapper.toKitchenDishResponse(dishBeforeEdit)).thenReturn(exceptedResponse);

        // Действие
        KitchenDishResponse actualResponse = dishService.addDishBalance(1L, request);

        // Проверка
        verify(dishRepository).findById(1L);
        assertThat(dishBeforeEdit.getBalance()).isEqualTo(dishAfterEdit.getBalance());
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(exceptedResponse);
    }

    @Test
    @DisplayName("Should throw exception when dish not found by id when add dish balance")
    void shouldThrowExceptionWhenDishNotFoundById() {
        // Подготовка
        AddDishBalanceRequest request = new AddDishBalanceRequest(0L);
        String expectedDetail = "Блюдо с идентификатором 1 не найдено";
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> dishService.addDishBalance(1L, request);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(dishRepository).findById(1L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

}