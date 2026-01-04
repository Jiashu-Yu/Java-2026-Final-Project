public class createdState implements DocumentState {
    Document doc;

    public createdState(Document doc) {
        this.doc = doc;
    }

    @Override
    public void initiateProject(Document doc) {
        doc.setState(doc.getWritingState());
        System.out.println("Project initiated, moved to Writing state.");
    }

    @Override
    public void submitContent(Document doc) {
        System.out.println("Cannot submit content in Created state.");
    }

    @Override
    public void reviewContent(Document doc, boolean passed) {
        System.out.println("Cannot review content in Created state.");
    }

    @Override
    public void approveContent(Document doc, boolean approved) {
        System.out.println("Cannot approve content in Created state.");
    }

    @Override
    public void revokeDocument(Document doc) {
        System.out.println("Cannot revoke document in Created state.");
    }

    @Override
    public void archiveDocument(Document doc) {
        System.out.println("Cannot archive document in Created state.");
    }

    @Override
    public String getStateName() {
        return "Created";
    }

    @Override
    public String toString() {
        return "waiting for initiation";
    }
}