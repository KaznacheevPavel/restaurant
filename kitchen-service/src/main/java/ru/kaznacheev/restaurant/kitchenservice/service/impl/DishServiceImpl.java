package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenDishResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.kitchenservice.dto.request.AddDishBalanceRequest;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;
import ru.kaznacheev.restaurant.kitchenservice.mapper.DishMapper;
import ru.kaznacheev.restaurant.kitchenservice.repository.DishRepository;
import ru.kaznacheev.restaurant.kitchenservice.service.DishService;

import java.util.List;

/**
 * Реализация интерфейса {@link DishService}.
 */
@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Override
    public KitchenDishResponse createDish(@Valid CreateKitchenDishRequest request) {
        if (dishRepository.existsByShortName(request.getShortName())) {
            throw new ConflictBaseException(ExceptionDetail.DISH_TITLE_ALREADY_EXISTS.format(request.getShortName()));
        }
        log.info("Создание блюда: {}", request.getShortName());
        Dish dish = Dish.builder()
                .id(request.getId())
                .shortName(request.getShortName())
                .composition(request.getComposition())
                .balance(0L)
                .build();
        dishRepository.save(dish);
        log.info("Блюдо - {} успешно создано, id: {}", request.getShortName(), dish.getId());
        return dishMapper.toKitchenDishResponse(dish);
    }

    @Transactional(readOnly = true)
    @Override
    public KitchenDishResponse getDishById(Long id) {
        log.debug("Получение информации о блюде, id: {}", id);
        return dishMapper.toKitchenDishResponse(dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundBaseException(ExceptionDetail.DISH_NOT_FOUND_BY_ID.format(id))));
    }

    @Transactional(readOnly = true)
    @Override
    public List<KitchenDishResponse> getAllDishes() {
        log.debug("Получение информации о всех блюдах");
        return dishMapper.toKitchenDishResponseList(dishRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Dish> getAllDishesByIds(Iterable<Long> ids) {
        log.debug("Получение информации о блюдах с id: {}", ids);
        return dishRepository.findAllById(ids);
    }

    @Transactional
    @Override
    public KitchenDishResponse addDishBalance(Long id, AddDishBalanceRequest request) {
        log.info("Добавление порций к блюду id: {}", id);
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundBaseException(ExceptionDetail.DISH_NOT_FOUND_BY_ID.format(id)));
        dish.setBalance(dish.getBalance() + request.getBalance());
        log.info("Порции к блюду с id: {} успешно добавлены в количестве: {}", id, request.getBalance());
        return dishMapper.toKitchenDishResponse(dish);
    }

}
