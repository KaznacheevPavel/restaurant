package ru.kaznacheev.restaurant.waiterservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "ru.kaznacheev.restaurant.waiterservice.feign")
@Configuration
public class FeignConfiguration {
}
