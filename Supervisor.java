public class Supervisor extends Observer {

    public Supervisor(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Supervisor";
    }
}