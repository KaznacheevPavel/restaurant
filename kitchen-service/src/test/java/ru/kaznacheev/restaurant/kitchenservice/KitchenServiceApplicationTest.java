package ru.kaznacheev.restaurant.kitchenservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class KitchenServiceApplicationTest {

    @Test
    @DisplayName("Should load context")
    void contextLoads() {
    }

}