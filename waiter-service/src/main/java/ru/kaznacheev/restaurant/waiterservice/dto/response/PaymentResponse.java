package ru.kaznacheev.restaurant.waiterservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.restaurant.waiterservice.entity.PaymentType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * DTO для ответа с информацией о платеже.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentResponse {

    /**
     * Идентификатор заказа.
     */
    private Long orderId;

    /**
     * Тип платежа из {@link PaymentType}.
     */
    private PaymentType type;

    /**
     * Дата и время платежа.
     */
    private OffsetDateTime paymentDate;

    /**
     * Сумма платежа.
     */
    private BigDecimal sum;

}
