package ru.kaznacheev.restaurant.waiterservice.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Сущность платежа.
 */
public class Payment {

    /**
     * Идентификатор заказа.
     */
    private Long orderId;

    /**
     * Тип платежа из {@link PaymentType}.
     */
    private PaymentType type;

    /**
     * Дата и время создания платежа.
     */
    private OffsetDateTime paymentDate;

    /**
     * Сумма платежа.
     */
    private BigDecimal sum;

}
