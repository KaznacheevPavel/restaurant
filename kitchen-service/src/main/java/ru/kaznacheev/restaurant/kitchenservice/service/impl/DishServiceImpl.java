package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;
import ru.kaznacheev.restaurant.kitchenservice.repository.DishRepository;
import ru.kaznacheev.restaurant.kitchenservice.service.DishService;

import java.util.List;

/**
 * Реализация интерфейса {@link DishService}.
 */
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

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

}
