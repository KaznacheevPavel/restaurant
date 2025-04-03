package ru.kaznacheev.restaurant.waiterservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;
import ru.kaznacheev.restaurant.waiterservice.mapper.DishMapper;
import ru.kaznacheev.restaurant.waiterservice.service.DishService;

import java.util.List;

/**
 * Реализация интерфейса {@link DishService}.
 */
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;

    /**
     * {@inheritDoc}
     *
     * @param dishTitles {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Dish> getAllDishesByTitles(List<String> dishTitles) {
        return dishMapper.getAllDishesByTitles(dishTitles);
    }

}
