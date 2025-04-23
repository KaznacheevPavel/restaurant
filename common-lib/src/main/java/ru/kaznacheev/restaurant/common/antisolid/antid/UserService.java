package ru.kaznacheev.restaurant.common.antisolid.antid;

/**
 * Описывает нарушение Dependency Inversion Principle.
 * <p>
 * Класс зависит от конкретной реализации сервиса уведомления.
 */
public class UserService {

    private EmailNotificationService emailNotificationService;

    /**
     * Отправляет уведомление.
     */
    public void sendNotification() {
        emailNotificationService.sendNotification();
    }

}

