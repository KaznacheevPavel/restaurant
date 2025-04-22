package ru.kaznacheev.restaurant.waiterservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreatePaymentRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.PaymentResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Payment;
import ru.kaznacheev.restaurant.waiterservice.entity.PaymentType;
import ru.kaznacheev.restaurant.waiterservice.mapper.PaymentMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.PaymentRepository;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;
import ru.kaznacheev.restaurant.waiterservice.service.PaymentService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;

/**
 * Реализация интерфейса {@link PaymentService}.
 */
@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final Clock clock;
    private final OrderService orderService;
    private final PaymentMapper paymentMapper;

    @Transactional
    @Override
    public PaymentResponse createPayment(@Valid CreatePaymentRequest request) {
        log.info("Создание платежа для заказа с id: {}", request.getOrderId());
        OrderResponse order = orderService.getOrderById(request.getOrderId());
        if (!order.getCost().equals(new BigDecimal(request.getSum()))) {
            throw new ConflictBaseException(ExceptionDetail.PAYMENT_AMOUNT_MISMATCH
                    .format(order.getId(), request.getSum(), order.getCost()));
        }
        orderService.paidForOrder(request.getOrderId());
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .type(PaymentType.valueOf(request.getType()))
                .paymentDate(OffsetDateTime.now(clock))
                .sum(new BigDecimal(request.getSum()))
                .build();
        paymentRepository.save(payment);
        log.info("Платеж для заказа с id: {} успешно создан на сумму: {}", payment.getOrderId(), payment.getSum());
        return paymentMapper.toPaymentResponse(payment);
    }

    @Transactional(readOnly = true)
    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        log.debug("Получение информации о платеже для заказа с id: {}", orderId);
        return paymentRepository.findByOrderId(orderId).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.PAYMENT_NOT_FOUND_BY_ID.format(orderId)));
    }

}
