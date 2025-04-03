package ru.kaznacheev.restaurant.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Конфигурация для работы с временем.
 */
@Configuration
public class ClockConfiguration {

    /**
     * Создает {@code Bean} {@link Clock}.
     *
     * @return {@link Clock}, возвращающий время в UTC
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

}
