import java.util.ArrayList;
import java.util.List;

public class Officer implements Observer {
    private String name;
    private List<String> notifications;

    public Officer(String name) {
        this.name = name;
        this.notifications = new ArrayList<>();
    }

    @Override
    public void update(Document doc, String message) {
        String notification = String.format("[%s] Document '%s': %s",
                getRole(), doc.getTitle(), message);
        notifications.add(notification);
        System.out.println(notification);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return "Officer";
    }

    public List<String> getNotifications() {
        return new ArrayList<>(notifications);
    }

    public void clearNotifications() {
        notifications.clear();
    }

    public void showNotifications() {
        System.out.println("\n=== Notifications for " + name + " (Officer) ===");
        if (notifications.isEmpty()) {
            System.out.println("No new notifications.");
        } else {
            for (int i = 0; i < notifications.size(); i++) {
                System.out.println((i + 1) + ". " + notifications.get(i));
            }
        }
        System.out.println("==========================================\n");
    }

    @Override
    public String toString() {
        return name + " (Officer)";
    }
}