public class Officer extends Observer {

    public Officer(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Officer";
    }
}