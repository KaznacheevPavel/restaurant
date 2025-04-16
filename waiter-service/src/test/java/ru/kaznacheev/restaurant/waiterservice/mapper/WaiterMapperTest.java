package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Gender;
import ru.kaznacheev.restaurant.waiterservice.entity.Waiter;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class WaiterMapperTest {

    private final WaiterMapper waiterMapper = Mappers.getMapper(WaiterMapper.class);

    @Test
    @DisplayName("Should map Waiter to WaiterResponse")
    void shouldMapToWaiterResponse() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T12:00:00Z"), ZoneId.systemDefault());
        Waiter waiter = Waiter.builder()
                .id(1L)
                .name("Иван")
                .employedAt(OffsetDateTime.now(clock))
                .sex(Gender.MALE)
                .build();
        WaiterResponse expected = WaiterResponse.builder()
                .id(1L)
                .name("Иван")
                .employedAt(OffsetDateTime.now(clock))
                .sex(Gender.MALE)
                .build();

        // Действие
        WaiterResponse actual = waiterMapper.toWaiterResponse(waiter);

        // Проверка
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return null when map Waiter to WaiterResponse if waiter is null")
    void shouldReturnNullWhenMapToWaiterResponse() {
        // Действие
        WaiterResponse actual = waiterMapper.toWaiterResponse(null);

        // Проверка
        assertThat(actual).isNull();
    }
}