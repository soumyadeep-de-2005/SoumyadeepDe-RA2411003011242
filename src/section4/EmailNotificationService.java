package section4;

public class EmailNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending: " + message);
    }
}
