package ru.kaznacheev.restaurant.waiterservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.restaurant.waiterservice.repository.WaiterRepository;
import ru.kaznacheev.restaurant.waiterservice.service.WaiterService;

/**
 * Реализация интерфейса {@link WaiterService}.
 */
@Service
@RequiredArgsConstructor
public class WaiterServiceImpl implements WaiterService {

    private final WaiterRepository waiterRepository;

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public boolean existsWaiterById(Long id) {
        return waiterRepository.existsWaiterById(id);
    }

}
