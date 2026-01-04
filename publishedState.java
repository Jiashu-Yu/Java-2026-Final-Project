public class publishedState implements DocumentState {
    Document doc;

    public publishedState(Document doc) {
        this.doc = doc;
    }

    @Override
    public void initiateProject(Document doc) {
        System.out.println("Document is already published. Cannot initiate a new project.");
    }

    @Override
    public void submitContent(Document doc) {
        System.out.println("Document is already published. Cannot submit new content.");
    }

    @Override
    public void reviewContent(Document doc, boolean passed) {
        System.out.println("Document is already published. Cannot review content.");
    }

    @Override
    public void approveContent(Document doc, boolean approved) {
        System.out.println("Document is already published. Cannot approve content.");
    }

    @Override
    public void revokeDocument(Document doc) {
        System.out.println("Document is already published. Cannot revoke document.");
    }

    @Override
    public void archiveDocument(Document doc) {
        System.out.println("Archiving the published document.");
        doc.setState(doc.getArchivedState());
    }

    @Override
    public String getStateName() {
        return "Published";
    }

    @Override
    public String toString() {
        return "Document is published.";
    }
}