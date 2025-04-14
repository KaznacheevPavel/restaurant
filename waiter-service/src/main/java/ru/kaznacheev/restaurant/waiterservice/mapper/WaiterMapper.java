package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Waiter;

/**
 * Маппер для преобразования официантов.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WaiterMapper {

    /**
     * Преобразует {@link Waiter} в {@link WaiterResponse}.
     *
     * @param waiter Сущность официанта
     * @return {@link WaiterResponse} с информацией об официанте
     */
    WaiterResponse toWaiterResponse(Waiter waiter);

}
