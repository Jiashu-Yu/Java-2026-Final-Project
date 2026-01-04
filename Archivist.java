public class Archivist extends Observer {

    public Archivist(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Archivist";
    }
}