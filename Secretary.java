public class Secretary extends Observer {

    public Secretary(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Secretary";
    }
}