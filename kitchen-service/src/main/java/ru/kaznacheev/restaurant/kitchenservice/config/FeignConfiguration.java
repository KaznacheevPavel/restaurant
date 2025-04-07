package ru.kaznacheev.restaurant.kitchenservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "ru.kaznacheev.restaurant.kitchenservice.feign")
@Configuration
public class FeignConfiguration {
}
