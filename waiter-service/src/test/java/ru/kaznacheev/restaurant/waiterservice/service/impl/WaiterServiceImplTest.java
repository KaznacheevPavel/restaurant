package ru.kaznacheev.restaurant.waiterservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateWaiterRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Gender;
import ru.kaznacheev.restaurant.waiterservice.entity.Waiter;
import ru.kaznacheev.restaurant.waiterservice.mapper.WaiterMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.WaiterRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WaiterServiceImplTest {

    @Mock
    private WaiterRepository waiterRepository;
    @Mock
    private Clock clock;
    @Mock
    private WaiterMapper waiterMapper;

    @InjectMocks
    private WaiterServiceImpl waiterService;

    @Test
    @DisplayName("Should create waiter and return WaiterResponse")
    void shouldCreateWaiter() {
        // Подготовка
        when(clock.instant()).thenReturn(Instant.parse("2024-01-01T12:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        CreateWaiterRequest request = new CreateWaiterRequest("Иван", "MALE");
        Waiter expectedWaiter = Waiter.builder()
                .id(1L)
                .name("Иван")
                .employedAt(OffsetDateTime.now(clock))
                .sex(Gender.MALE)
                .build();
        WaiterResponse expectedResponse = WaiterResponse.builder()
                .id(1L)
                .name("Иван")
                .employedAt(OffsetDateTime.now(clock))
                .sex(Gender.MALE)
                .build();
        doAnswer(invocation -> {
            Waiter waiter = invocation.getArgument(0);
            waiter.setId(1L);
            return waiter;
        }).when(waiterRepository).save(any(Waiter.class));
        when(waiterMapper.toWaiterResponse(any(Waiter.class))).thenReturn(expectedResponse);

        // Действие
        WaiterResponse actualResponse = waiterService.createWaiter(request);

        // Проверка
        ArgumentCaptor<Waiter> waiterCaptor = ArgumentCaptor.forClass(Waiter.class);
        verify(waiterRepository).save(waiterCaptor.capture());
        assertThat(waiterCaptor.getValue()).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedWaiter);
        verify(waiterMapper).toWaiterResponse(waiterCaptor.capture());
        assertThat(waiterCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedWaiter);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return WaiterResponse when get waiter by id")
    void shouldReturnWaiterResponse() {
        // Подготовка
        when(clock.instant()).thenReturn(Instant.parse("2024-01-01T12:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        WaiterResponse expectedResponse = WaiterResponse.builder()
                .id(1L)
                .name("Иван")
                .employedAt(OffsetDateTime.now(clock))
                .sex(Gender.MALE)
                .build();
        when(waiterRepository.findById(1L)).thenReturn(Optional.of(expectedResponse));

        // Действие
        WaiterResponse actualResponse = waiterService.getWaiterById(1L);

        // Проверка
        verify(waiterRepository).findById(1L);
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should throw exception if waiter not exists")
    void shouldThrowExceptionIfWaiterNotExists() {
        // Подготовка
        String expectedDetail = "Официант с идентификатором 5 не найден";
        when(waiterRepository.findById(5L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> waiterService.getWaiterById(5L);

        // Проверка
        NotFoundBaseException actualException = assertThrows(NotFoundBaseException.class, executable);
        verify(waiterRepository).findById(5L);
        assertThat(actualException.getDetail()).isEqualTo(expectedDetail);
    }

    @Test
    @DisplayName("Should return list of WaiterShortInfoResponse when getting all waiters")
    void shouldReturnWaiterShortInfoResponseList() {
        // Подготовка
        List<WaiterShortInfoResponse> expected = List.of(
                new WaiterShortInfoResponse(1L, "Иван"),
                new WaiterShortInfoResponse(2L, "Петр")
        );
        when(waiterRepository.findAll()).thenReturn(expected);

        // Действие
        List<WaiterShortInfoResponse> actual = waiterService.getAllWaiters();

        // Проверка
        verify(waiterRepository).findAll();
        assertThat(actual).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return true if waiter exists")
    void shouldReturnTrueIfWaiterExists() {
        // Подготовка
        when(waiterRepository.existsById(1L)).thenReturn(true);

        // Действие
        boolean actual = waiterService.existsWaiterById(1L);

        // Проверка
        verify(waiterRepository).existsById(1L);
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Should return false if waiter not exists")
    void shouldReturnFalseIfWaiterNotExists() {
        // Подготовка
        when(waiterRepository.existsById(1L)).thenReturn(false);

        // Действие
        boolean actual = waiterService.existsWaiterById(1L);

        // Проверка
        verify(waiterRepository).existsById(1L);
        assertThat(actual).isFalse();
    }

}