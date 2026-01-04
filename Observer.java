public interface Observer {
    void update(Document doc, String message);
    String getName();
    String getRole();
}