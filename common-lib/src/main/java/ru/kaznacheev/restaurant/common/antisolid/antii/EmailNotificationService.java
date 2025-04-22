package ru.kaznacheev.restaurant.common.antisolid.antii;

/**
 * Описывает нарушение Interface Segregation Principle.
 * <p>
 * Класс, работающий только с email уведомлениями вынужден реализовывать метод для sms.
 */
public class EmailNotificationService implements NotificationService {

    /**
     * Отправляет email уведомление.
     */
    @Override
    public void sendEmail() {

    }

    /**
     * Отправляет sms уведомление.
     */
    @Override
    public void sendSms() {
        throw new UnsupportedOperationException();
    }

}

