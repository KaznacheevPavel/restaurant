package ru.kaznacheev.restaurant.waiterservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Waiter service API",
                description = "API сервиса официантов для ресторана",
                version = "1.0.0",
                contact = @Contact(
                        name = "Казначеев Павел",
                        url = "https://t.me/kaznacheevp"
                )
        )
)
public class SpringDocConfiguration {
}
