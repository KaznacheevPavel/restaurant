package ru.kaznacheev.restaurant.waiterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения waiter-service.
 */
@SpringBootApplication
public class WaiterServiceApplication {

	/**
	 * Точка входа для запуска Spring Boot приложения.
	 *
	 * @param args Аргументы командной строки, переданные при запуске приложения.
	 */
	public static void main(String[] args) {
		SpringApplication.run(WaiterServiceApplication.class, args);
	}

}
