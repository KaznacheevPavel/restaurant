package ru.kaznacheev.restaurant.waiterservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class WaiterServiceApplicationTest {

    @Test
    @DisplayName("Should load context")
    void contextLoads() {
    }

}