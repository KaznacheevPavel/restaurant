package ru.kaznacheev.restaurant.waiterservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreatePaymentRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.PaymentResponse;

/**
 * Интерфейс сервиса для работы с платежами.
 */
public interface PaymentService {

    /**
     * Создает новый платеж.
     *
     * @param request DTO с информацией о новом платеже
     * @return {@link PaymentResponse} с информацией о созданном платеже
     */
    PaymentResponse createPayment(@Valid CreatePaymentRequest request);

    /**
     * Возвращает информацию о платеже по идентификатору заказа.
     *
     * @param orderId Идентификатор заказа
     * @return {@link PaymentResponse} с информацией о платеже
     */
    PaymentResponse getPaymentByOrderId(Long orderId);

}
