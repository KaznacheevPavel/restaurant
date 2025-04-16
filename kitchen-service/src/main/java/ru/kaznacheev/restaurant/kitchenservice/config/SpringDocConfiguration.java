package ru.kaznacheev.restaurant.kitchenservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Kitchen service API",
                description = "API сервиса кухни для ресторана",
                version = "1.0.0",
                contact = @Contact(
                        name = "Казначеев Павел",
                        url = "https://t.me/kaznacheevp"
                )
        )
)
public class SpringDocConfiguration {
}
