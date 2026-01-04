public class archivedState implements DocumentState {
    Document doc;

    public archivedState(Document doc) {
        this.doc = doc;
    }

    @Override
    public void initiateProject(Document doc) {
        System.out.println("Cannot initiate project. Document is archived.");
    }

    @Override
    public void submitContent(Document doc) {
        System.out.println("Cannot submit content. Document is archived.");
    }

    @Override
    public void reviewContent(Document doc, boolean passed) {
        System.out.println("Cannot review content. Document is archived.");
    }

    @Override
    public void approveContent(Document doc, boolean approved) {
        System.out.println("Cannot approve content. Document is archived.");
    }

    @Override
    public void revokeDocument(Document doc) {
        System.out.println("Cannot revoke document. Document is archived.");
    }

    @Override
    public void archiveDocument(Document doc) {
        System.out.println("Document is already archived.");
    }

    @Override
    public String getStateName() {
        return "Archived";
    }

    @Override
    public String toString() {
        return "Document is archived.";
    }
}