public class writingState implements DocumentState{
    Document doc;

    public writingState(Document doc) {
        this.doc = doc;
    }

    public void initiateProject(Document doc) {
        System.out.println("Project already initiated, currently in Writing state.");
    }

    public void submitContent(Document doc) {
        doc.setState(doc.getReviewState());
        System.out.println("Content submitted, moved to Review state.");
    }

    public void reviewContent(Document doc, boolean passed) {
        System.out.println("Cannot review content in Writing state.");
    }

    public void approveContent(Document doc, boolean approved) {
        System.out.println("Cannot approve content in Writing state.");
    }

    public void revokeDocument(Document doc) {
        System.out.println("Cannot revoke document in Writing state.");
    }

    public void archiveDocument(Document doc) {
        System.out.println("Cannot archive document in Writing state.");
    }

    public String getStateName() {
        return "Writing";
    }

    public String toString() {
        return "currently writing content";
    }
}