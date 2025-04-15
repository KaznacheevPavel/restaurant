package ru.kaznacheev.restaurant.waiterservice.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.waiterservice.dto.response.PaymentResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Payment;

import java.util.Optional;

/**
 * Репозиторий для работы с платежами.
 */
@Mapper
@Repository
public interface PaymentRepository {

    /**
     * Сохраняет платеж.
     *
     * @param payment Сущность платежа
     */
    void save(@Param("payment")Payment payment);

    /**
     * Возвращает информацию о платеже по идентификатору заказа.
     *
     * @param orderId Идентификатор заказа
     * @return {@link Optional} {@link PaymentResponse} с информацией о платеже
     */
    Optional<PaymentResponse> findByOrderId(@Param("orderId") Long orderId);

}
