import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class Document {
    DocumentState createdState;
    DocumentState writingState;
    DocumentState reviewState;
    DocumentState approvedState;
    DocumentState publishedState;
    DocumentState revokedState;
    DocumentState archivedState;

    private String title;
    private String content;
    private DocumentState state;

    public static int documentCounter = 0;

    public Document(String title) {
        this.title = title;
        this.content = "";
        createdState = new createdState(this);
        writingState = new writingState(this);
        reviewState = new reviewState(this);
        approvedState = new approvedState(this);
        publishedState = new publishedState(this);
        revokedState = new revokedState(this);
        archivedState = new archivedState(this);
        state = createdState;
        documentCounter++;
    }

    public void setState(DocumentState state) {
        this.state = state;
    }

    public void initiateProject() {
        state.initiateProject(this);
    }

    public void submitContent() {
        state.submitContent(this);
    }

    public void reviewContent(boolean passed) {
        state.reviewContent(this, passed);
    }

    public void approveContent(boolean approved) {
        state.approveContent(this, approved);
    }

    public void revokeDocument() {
        state.revokeDocument(this);
    }

    public void archiveDocument() {
        state.archiveDocument(this);
    }

    public DocumentState getState() {
        return state;
    }

    public DocumentState getCreatedState() {
        return createdState;
    }

    public DocumentState getWritingState() {
        return writingState;
    }

    public DocumentState getReviewState() {
        return reviewState;
    }

    public DocumentState getApprovedState() {
        return approvedState;
    }

    public DocumentState getPublishedState() {
        return publishedState;
    }

    public DocumentState getRevokedState() {
        return revokedState;
    }

    public DocumentState getArchivedState() {
        return archivedState;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return "Document: " + title + " is currently in state: " + state.getStateName();
    }
}