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
    public void update(String message) {
        notifications.add(message);
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
        int count = notifications.size();
        notifications.clear();
        System.out.println("\n[Success] " + count + " notification(s) cleared.");
    }

    // 获取未读通知数量
    public int getUnreadCount() {
        return notifications.size();
    }

    // 检查是否有未读通知
    public boolean hasUnreadNotifications() {
        return !notifications.isEmpty();
    }
}