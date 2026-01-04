import java.util.*;

public abstract class Observer {
    protected String name;
    protected List<String> notifications;

    public Observer(String name) {
        this.name = name;
        this.notifications = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public abstract String getRole();

    // 静默接收通知（不打印）
    public void update(Document doc, String message) {
        String notification = "[" + doc.getTitle() + "] " + message;
        notifications.add(notification);
    }

    // 查看所有通知
    public void showNotifications() {
        if (notifications.isEmpty()) {
            System.out.println("\n[Info] No notifications.");
            return;
        }

        System.out.println("\n========== Your Notifications ==========");
        System.out.println("Total: " + notifications.size() + " notification(s)\n");

        for (int i = 0; i < notifications.size(); i++) {
            System.out.println((i + 1) + ". " + notifications.get(i));
        }

        System.out.println("========================================");
    }

    // 清空通知
    public void clearNotifications() {
        if (notifications.isEmpty()) {
            System.out.println("\n[Info] No notifications to clear.");
            return;
        }

        int count = notifications.size();
        notifications.clear();
        System.out.println("\n[Success] " + count + " notification(s) cleared.");
    }

    // 获取通知数量
    public int getNotificationCount() {
        return notifications.size();
    }

    // 检查是否有通知
    public boolean hasNotifications() {
        return !notifications.isEmpty();
    }
}