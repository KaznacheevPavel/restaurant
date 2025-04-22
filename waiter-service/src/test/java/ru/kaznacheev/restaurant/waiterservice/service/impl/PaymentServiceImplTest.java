package ru.kaznacheev.restaurant.waiterservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreatePaymentRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.PaymentResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.waiterservice.entity.Payment;
import ru.kaznacheev.restaurant.waiterservice.entity.PaymentType;
import ru.kaznacheev.restaurant.waiterservice.mapper.PaymentMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.PaymentRepository;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private Clock clock;
    @Mock
    private OrderService orderService;
    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    @DisplayName("Should create payment and return PaymentResponse")
    void shouldCreatePayment() {
        // Подготовка
        when(clock.instant()).thenReturn(Instant.parse("2024-01-01T12:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        CreatePaymentRequest request = CreatePaymentRequest.builder()
                .orderId(12L)
                .type("CARD")
                .sum("100.00")
                .build();
        Payment expectedPayment = Payment.builder()
                .orderId(12L)
                .type(PaymentType.CARD)
                .paymentDate(OffsetDateTime.now(clock))
                .sum(new BigDecimal("100.00"))
                .build();
        PaymentResponse expected = PaymentResponse.builder().
                orderId(12L)
                .type(PaymentType.CARD)
                .paymentDate(OffsetDateTime.now(clock))
                .sum(new BigDecimal("100.00"))
                .build();
        OrderResponse order = OrderResponse.builder()
                .id(12L)
                .status(OrderStatus.IN_PROGRESS)
                .createdAt(OffsetDateTime.now(clock))
                .waiterId(1L)
                .tableNumber("3a")
                .cost(new BigDecimal("100.00"))
                .composition(List.of(
                        OrderPositionResponse.builder()
                                .dishId(1L)
                                .name("Борщ с пампушками")
                                .amount(1L)
                                .build(),
                        OrderPositionResponse.builder()
                                .dishId(2L)
                                .name("Солянка")
                                .amount(2L)
                                .build()))
                .build();
        when(orderService.getOrderById(12L)).thenReturn(order);
        when(paymentMapper.toPaymentResponse(any(Payment.class))).thenReturn(expected);

        // Действие
        PaymentResponse actual = paymentService.createPayment(request);

        // Проверка
        verify(orderService).getOrderById(12L);
        verify(orderService).paidForOrder(12L);
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        assertThat(paymentCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedPayment);
        verify(paymentMapper).toPaymentResponse(paymentCaptor.capture());
        assertThat(paymentCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedPayment);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should throw exception if order cost and payment cost mismatch")
    void shouldThrowExceptionIfOrderCostMismatch() {
        // Подготовка
        CreatePaymentRequest request = CreatePaymentRequest.builder()
                .orderId(12L)
                .type("CARD")
                .sum("99.00")
                .build();
        OrderResponse order = OrderResponse.builder()
                .id(12L)
                .cost(new BigDecimal("100.00"))
                .build();
        String expectedDetail = "Неверная сумма оплаты для заказа 12, получено 99.00, требуется 100.00";
        when(orderService.getOrderById(request.getOrderId())).thenReturn(order);

        // Действие
        Executable executable = () -> paymentService.createPayment(request);

        // Проверка
        ConflictBaseException actualException = assertThrows(ConflictBaseException.class, executable);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should return payment response when getting by order id")
    void shouldReturnPaymentResponse() {
        // Подготовка
        when(clock.instant()).thenReturn(Instant.parse("2024-01-01T12:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        Payment payment = Payment.builder()
                .orderId(12L)
                .type(PaymentType.CARD)
                .paymentDate(OffsetDateTime.now(clock))
                .sum(new BigDecimal("100.00"))
                .build();
        PaymentResponse expected = PaymentResponse.builder().
                orderId(12L)
                .type(PaymentType.CARD)
                .paymentDate(OffsetDateTime.now(clock))
                .sum(new BigDecimal("100.00"))
                .build();
        when(paymentRepository.findByOrderId(12L)).thenReturn(Optional.of(expected));

        // Действие
        PaymentResponse actual = paymentService.getPaymentByOrderId(12L);

        // Проверка
        verify(paymentRepository).findByOrderId(12L);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should throw exception if payment not found")
    void shouldThrowExceptionIfPaymentNotFound() {
        // Подготовка
        when(paymentRepository.findByOrderId(12L)).thenReturn(Optional.empty());
        String expectedDetail = "Платеж с идентификатором 12 не найден";

        // Действие
        Executable executable = () -> paymentService.getPaymentByOrderId(12L);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(paymentRepository).findByOrderId(12L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

}