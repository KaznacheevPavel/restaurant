package ru.kaznacheev.restaurant.common.antisolid.antio;

/**
 * Описывает нарушение Open/Closed Principle.
 * <p>
 * При добавлении нового типа уведомления, класс должен быть изменен.
 */
public class NotificationService {

    /**
     * Определяет сообщение для отправки.
     *
     * @param type Тип уведомления
     * @return Сообщение для отправки
     */
    public String getMessage(NotificationType type) {
        switch (type) {
            case REGISTRATION -> {
                return "Registration message";
            }
            case EDIT_PASSWORD -> {
                return "Edit password message";
            }
            case NEW_ORDER -> {
                return "New order message";
            }
            case REJECT_ORDER -> {
                return "Reject order message";
            }
            default -> {
                return "Unknown message";
            }
        }
    }

}
