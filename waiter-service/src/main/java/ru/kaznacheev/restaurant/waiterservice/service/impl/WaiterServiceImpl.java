package ru.kaznacheev.restaurant.waiterservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateWaiterRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Gender;
import ru.kaznacheev.restaurant.waiterservice.entity.Waiter;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.waiterservice.mapper.WaiterMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.WaiterRepository;
import ru.kaznacheev.restaurant.waiterservice.service.WaiterService;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Реализация интерфейса {@link WaiterService}.
 */
@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class WaiterServiceImpl implements WaiterService {

    private final WaiterRepository waiterRepository;
    private final Clock clock;
    private final WaiterMapper waiterMapper;

    @Override
    public WaiterResponse createWaiter(@Valid CreateWaiterRequest request) {
        log.info("Создание официанта с именем: {}", request.getName());
        Waiter waiter = Waiter.builder()
                .name(request.getName())
                .employedAt(OffsetDateTime.now(clock))
                .sex(Gender.valueOf(request.getSex()))
                .build();
        waiterRepository.save(waiter);
        log.info("Официант с именем: {} успешно создан, id: {}", request.getName(), waiter.getId());
        return waiterMapper.toWaiterResponse(waiter);
    }

    @Transactional(readOnly = true)
    @Override
    public WaiterResponse getWaiterById(Long id) {
        log.debug("Получение информации об официанте с id: {}", id);
        return waiterRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.WAITER_NOT_FOUND_BY_ID.format(id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<WaiterShortInfoResponse> getAllWaiters() {
        log.debug("Получение информации о всех официантах");
        return waiterRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsWaiterById(Long id) {
        log.debug("Проверка существования официанта с id: {}", id);
        return waiterRepository.existsById(id);
    }

}
