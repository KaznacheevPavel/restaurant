package ru.kaznacheev.restaurant.kitchenservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для сканирования пакетов.
 */
@Configuration
@ComponentScan(basePackages = {
        "ru.kaznacheev.restaurant.kitchenservice",
        "ru.kaznacheev.restaurant.common"
})
public class PackagesScanConfiguration {
}
