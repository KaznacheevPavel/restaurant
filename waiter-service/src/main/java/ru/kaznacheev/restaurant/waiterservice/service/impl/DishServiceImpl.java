package ru.kaznacheev.restaurant.waiterservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateDishRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.waiterservice.mapper.DishMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.DishRepository;
import ru.kaznacheev.restaurant.waiterservice.service.DishService;
import ru.kaznacheev.restaurant.waiterservice.service.KitchenCommunicationService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация интерфейса {@link DishService}.
 */
@Service
@Validated
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final KitchenCommunicationService kitchenCommunicationService;
    private final DishMapper dishMapper;

    /**
     * {@inheritDoc}
     *
     * @param request {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ConflictBaseException Если блюдо с таким названием уже существует
     */
    @Transactional
    @Override
    public DishResponse createDish(@Valid CreateDishRequest request) {
        if (dishRepository.existsByName(request.getName())) {
            throw new ConflictBaseException(ExceptionDetail.DISH_TITLE_ALREADY_EXISTS.format(request.getName()));
        }
        Dish dish = Dish.builder()
                .name(request.getName())
                .cost(new BigDecimal(request.getCost()))
                .build();
        dishRepository.save(dish);
        sendDishToKitchen(dish.getId(), request);
        return dishMapper.toDishResponse(dish);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NotFoundBaseException Если блюдо не было найдено
     */
    @Transactional(readOnly = true)
    @Override
    public DishResponse getDishById(Long id) {
        return dishRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.DISH_NOT_FOUND_BY_ID.format(id)));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<DishResponse> getAllDishes() {
        return dishRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * @param names {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<DishResponse> getAllDishesByNames(List<String> names) {
        return dishRepository.findAllByNames(names);
    }

    /**
     * Создает запрос с новым блюдом и отправляет на кухню.
     *
     * @param id Идентификатор блюда
     * @param dish DTO с информацией о блюде
     */
    private void sendDishToKitchen(Long id, CreateDishRequest dish) {
        CreateKitchenDishRequest request = CreateKitchenDishRequest.builder()
                .id(id)
                .shortName(dish.getShortName())
                .composition(dish.getComposition())
                .build();
        kitchenCommunicationService.sendDishToKitchen(request);
    }

}
