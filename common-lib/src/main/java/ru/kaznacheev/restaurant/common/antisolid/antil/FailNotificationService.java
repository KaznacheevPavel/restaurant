package ru.kaznacheev.restaurant.common.antisolid.antil;

/**
 * Описывает нарушение Liskov Substitution Principle.
 * <p>
 * Класс-наследник противоречит базовому классу.
 */
public class FailNotificationService extends NotificationService {

    /**
     * Отправляет уведомление.
     */
    @Override
    public void send() {
        throw new UnsupportedOperationException();
    }

}
