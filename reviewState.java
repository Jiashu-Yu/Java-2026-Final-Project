public class reviewState implements DocumentState {
    Document doc;

    public reviewState(Document doc) {
        this.doc = doc;
    }

    @Override
    public void initiateProject(Document doc) {
        System.out.println("Project already initiated.");
    }

    @Override
    public void submitContent(Document doc) {
        System.out.println("Content already submitted, under review.");
    }

    @Override
    public void reviewContent(Document doc, boolean passed) {
        if (passed) {
            System.out.println("Content passed review. Moving to approved state.");
            doc.setState(doc.getApprovedState());
        } else {
            System.out.println("Content failed review. Returning to writing state.");
            doc.setState(doc.getWritingState());
        }
    }

    @Override
    public void approveContent(Document doc, boolean approved) {
        System.out.println("Content is under review, cannot approve yet.");
    }

    @Override
    public void revokeDocument(Document document) {
        System.out.println("Cannot revoke document under review.");
    }

    @Override
    public void archiveDocument(Document document) {
        System.out.println("Cannot archive document under review.");
    }

    @Override
    public String getStateName() {
        return "Under Review";
    }

    @Override
    public String toString() {
        return "Document is under review.";
    }

}