public class revokedState implements DocumentState {
    private Document doc;

    public revokedState(Document doc) {
        this.doc = doc;
    }

    @Override
    public void initiateProject(Document doc) {
        System.out.println("Cannot initiate project. Document is revoked.");
    }

    @Override
    public void submitContent(Document doc) {
        System.out.println("Cannot submit content. Document is revoked.");
    }

    @Override
    public void reviewContent(Document doc, boolean passed) {
        System.out.println("Cannot review content. Document is revoked.");
    }

    @Override
    public void approveContent(Document doc, boolean approved) {
        System.out.println("Cannot approve content. Document is revoked.");
    }

    @Override
    public void revokeDocument(Document doc) {
        System.out.println("Document is already revoked.");
    }

    @Override
    public void archiveDocument(Document doc) {
        System.out.println("Archiving the revoked document.");
        doc.setState(doc.getArchivedState());
    }

    @Override
    public String getStateName() {
        return "Revoked";
    }

    @Override
    public String toString() {
        return "Document is revoked.";
    }
}