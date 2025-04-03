package ru.kaznacheev.restaurant.waiterservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.restaurant.waiterservice.mapper.WaiterMapper;
import ru.kaznacheev.restaurant.waiterservice.service.WaiterService;

/**
 * Реализация интерфейса {@link WaiterService}.
 */
@Service
@RequiredArgsConstructor
public class WaiterServiceImpl implements WaiterService {

    private final WaiterMapper waiterMapper;

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public boolean existsWaiterById(Long id) {
        return waiterMapper.existsWaiterById(id);
    }

}
