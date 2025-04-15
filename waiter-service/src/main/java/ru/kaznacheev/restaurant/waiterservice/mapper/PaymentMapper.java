package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.restaurant.waiterservice.dto.response.PaymentResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Payment;

/**
 * Маппер для преобразования платежей.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    /**
     * Преобразует {@link Payment} в {@link PaymentResponse}.
     *
     * @param payment Сущность платежа
     * @return {@link PaymentResponse} с информацией о платеже
     */
    PaymentResponse toPaymentResponse(Payment payment);

}
