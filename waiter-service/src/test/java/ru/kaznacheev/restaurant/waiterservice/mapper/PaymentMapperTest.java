package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.kaznacheev.restaurant.waiterservice.dto.response.PaymentResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Payment;
import ru.kaznacheev.restaurant.waiterservice.entity.PaymentType;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentMapperTest {

    private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    @DisplayName("Should map Payment to PaymentResponse")
    void shouldMapToPaymentResponse() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T12:00:00Z"), ZoneId.systemDefault());
        Payment payment = createPayment(clock);
        PaymentResponse expected = createPaymentResponse(clock);

        // Действие
        PaymentResponse actual = paymentMapper.toPaymentResponse(payment);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return null when map Payment to PaymentResponse if payment is null")
    void shouldReturnNullWhenMapToPaymentResponse() {
        // Действие
        PaymentResponse actual = paymentMapper.toPaymentResponse(null);

        // Проверка
        assertThat(actual).isNull();
    }

    private Payment createPayment(Clock clock) {
        return Payment.builder()
                .orderId(12L)
                .type(PaymentType.CARD)
                .paymentDate(OffsetDateTime.now(clock))
                .sum(new BigDecimal("100.00"))
                .build();
    }

    private PaymentResponse createPaymentResponse(Clock clock) {
        return PaymentResponse.builder()
                .orderId(12L)
                .type(PaymentType.CARD)
                .paymentDate(OffsetDateTime.now(clock))
                .sum(new BigDecimal("100.00"))
                .build();
    }

}