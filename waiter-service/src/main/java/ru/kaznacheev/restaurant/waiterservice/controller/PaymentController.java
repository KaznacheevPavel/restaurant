package ru.kaznacheev.restaurant.waiterservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreatePaymentRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.PaymentResponse;
import ru.kaznacheev.restaurant.waiterservice.service.PaymentService;

/**
 * Контроллер для обработки запросов, связанных с платежами.
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Создает новый платеж.
     *
     * @param request DTO с информацией о новом платеже
     * @return {@link PaymentResponse} с информацией о созданном платеже
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(@RequestBody CreatePaymentRequest request) {
        return paymentService.createPayment(request);
    }

    /**
     * Возвращает информацию о платеже по идентификатору заказа.
     *
     * @param orderId Идентификатор заказа
     * @return {@link PaymentResponse} с информацией о платеже
     */
    @GetMapping("/{orderId}")
    public PaymentResponse getPayment(@PathVariable Long orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }

}
