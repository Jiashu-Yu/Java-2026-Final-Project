public class approvedState implements DocumentState {
    Document doc;

    public approvedState(Document doc) {
        this.doc = doc;
    }

    @Override
    public void initiateProject(Document doc) {
        System.out.println("Document is waiting for approval. Cannot initiate project.");
    }

    @Override
    public void submitContent(Document doc) {
        System.out.println("Document is waiting for approval. Cannot submit content.");
    }

    @Override
    public void reviewContent(Document doc, boolean passed) {
        System.out.println("Document is waiting for approval. Cannot review content.");
    }

    @Override
    public void approveContent(Document doc, boolean approved) {
        if (approved) {
            System.out.println("Approving the document. Moving to Published state.");
            doc.setState(doc.getPublishedState());
        } else {
            System.out.println("Document not approved. Returning to writing state.");
            doc.setState(doc.getWritingState());
        }
    }

    @Override
    public void revokeDocument(Document doc) {
        System.out.println("Revoking the document.");
        doc.setState(doc.getRevokedState());
    }

    @Override
    public void archiveDocument(Document doc) {
        System.out.println("Cannot archive document in Approved state.");
    }

    @Override
    public String getStateName() {
        return "To Be Approved";
    }

    @Override
    public String toString() {
        return "Document is waiting for approval.";
    }
}