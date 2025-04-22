package ru.kaznacheev.restaurant.common.antisolid.antis;

/**
 * Описывает нарушение Single Responsibility Principle.
 * <p>
 * Класс имеет несколько причин для изменения, если нужно будет изменить логику создания/удаления пользователей -
 * нужно будет изменить этот класс, а также если нужно будет изменить логику создания заказа, придется тоже
 * изменять этот класс.
 */
public class UserService {

    /**
     * Создает пользователя.
     */
    public void createUser() {

    }

    /**
     * Удаляет пользователя.
     */
    public void deleteUser() {

    }

    /**
     * Создает заказ.
     */
    public void createOrder() {

    }

}
