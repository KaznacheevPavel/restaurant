package ru.kaznacheev.restaurant.waiterservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.restaurant.waiterservice.entity.PaymentType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * DTO для ответа с информацией о платеже.
 */
@Schema(description = "Информация о платеже")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentResponse {

    /**
     * Идентификатор заказа.
     */
    @Schema(description = "Идентификатор заказа", example = "1")
    private Long orderId;

    /**
     * Тип платежа из {@link PaymentType}.
     */
    @Schema(description = "Тип платежа", example = "CARD")
    private PaymentType type;

    /**
     * Дата и время платежа.
     */
    @Schema(description = "Дата и время платежа", example = "2025-04-04T17:33:54.000Z")
    private OffsetDateTime paymentDate;

    /**
     * Сумма платежа.
     */
    @Schema(description = "Сумма платежа", example = "1541.99")
    private BigDecimal sum;

}
