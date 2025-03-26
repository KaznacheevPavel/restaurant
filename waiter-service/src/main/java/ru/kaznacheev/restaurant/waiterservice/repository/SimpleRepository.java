package ru.kaznacheev.restaurant.waiterservice.repository;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория.
 */
public interface SimpleRepository<ID, T> {

    /**
     * Сохраняет сущность.
     *
     * @param entity Сущность, которую нужно сохранить
     */
    void save(T entity);
    /**
     * Возвращает сущность по его идентификатору.
     *
     * @param id Идентификатор сущности
     * @return {@link Optional} с сущностью
     */
    Optional<T> getOrderById(ID id);


    /**
     * Возвращает список всех сущностей.
     *
     * @return Список сущностей
     */
    List<T> getAllOrders();
}
