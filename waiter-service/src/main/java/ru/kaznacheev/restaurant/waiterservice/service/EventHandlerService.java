package ru.kaznacheev.restaurant.waiterservice.service;

/**
 * Интерфейс для обработки событий определенного типа.
 */
public interface EventHandlerService<T> {

    /**
     * Обрабатывает событие.
     *
     * @param event Событие
     */
    void processEvent(T event);

}
