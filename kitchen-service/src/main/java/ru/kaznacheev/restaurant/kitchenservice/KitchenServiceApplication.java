package ru.kaznacheev.restaurant.kitchenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения kitchen-service.
 */
@SpringBootApplication
public class KitchenServiceApplication {

    /**
     * Точка входа для запуска Spring Boot приложения.
     *
     * @param args Аргументы командной строки, переданные при запуске приложения.
     */
    public static void main(String[] args) {
        SpringApplication.run(KitchenServiceApplication.class, args);
    }

}
