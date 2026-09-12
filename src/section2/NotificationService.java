package section2;

/**
 * Notification service abstraction (OCP + DIP).
 */
public interface NotificationService {
    void send(String message);
}
