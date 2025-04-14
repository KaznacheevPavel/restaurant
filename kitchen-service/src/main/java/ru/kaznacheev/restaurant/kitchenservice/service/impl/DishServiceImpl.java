package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@Validated
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    /**
     * {@inheritDoc}
     *
     * @param request {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ConflictBaseException Если блюдо с таким сокращенным названием уже существует
     */
    @Override
    public KitchenDishResponse createDish(@Valid CreateKitchenDishRequest request) {
        if (dishRepository.existsByShortName(request.getShortName())) {
            throw new ConflictBaseException(ExceptionDetail.DISH_TITLE_ALREADY_EXISTS.format(request.getShortName()));
        }
        Dish dish = Dish.builder()
                .id(request.getId())
                .shortName(request.getShortName())
                .composition(request.getComposition())
                .balance(0L)
                .build();
        dishRepository.save(dish);
        return dishMapper.toKitchenDishResponse(dish);
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
    public KitchenDishResponse getDishById(Long id) {
        return dishMapper.toKitchenDishResponse(dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundBaseException(ExceptionDetail.DISH_NOT_FOUND_BY_ID.format(id))));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<KitchenDishResponse> getAllDishes() {
        return dishMapper.toKitchenDishResponseList(dishRepository.findAll());
    }

    /**
     * {@inheritDoc}
     *
     * @param ids {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Dish> getAllDishesByIds(Iterable<Long> ids) {
        return dishRepository.findAllById(ids);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @param request {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NotFoundBaseException Если блюдо не было найдено
     */
    @Transactional
    @Override
    public KitchenDishResponse addDishBalance(Long id, AddDishBalanceRequest request) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundBaseException(ExceptionDetail.DISH_NOT_FOUND_BY_ID.format(id)));
        dish.setBalance(dish.getBalance() + request.getBalance());
        return dishMapper.toKitchenDishResponse(dish);
    }

}
