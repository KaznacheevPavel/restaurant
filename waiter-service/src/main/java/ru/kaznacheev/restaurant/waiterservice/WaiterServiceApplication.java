package ru.kaznacheev.restaurant.waiterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Основной класс приложения waiter-service.
 */
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {
		"ru.kaznacheev.restaurant.waiterservice",
		"ru.kaznacheev.restaurant.common"
})
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
