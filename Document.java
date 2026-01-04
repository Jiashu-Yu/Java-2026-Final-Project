import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Document {
    // 状态对象
    DocumentState createdState;
    DocumentState writingState;
    DocumentState reviewState;
    DocumentState approvedState;
    DocumentState publishedState;
    DocumentState revokedState;
    DocumentState archivedState;

    // 基本属性
    private final String title;
    private String content;
    private DocumentState state;

    // 时间标签 - 记录每个环节的处理时间
    private Map<String, LocalDateTime> timestamps;

    public static int documentCounter = 0;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Document(String title) {
        this.title = title;
        this.content = "";
        this.timestamps = new LinkedHashMap<>(); // 保持插入顺序

        // 初始化所有状态
        createdState = new createdState(this);
        writingState = new writingState(this);
        reviewState = new reviewState(this);
        approvedState = new approvedState(this);
        publishedState = new publishedState(this);
        revokedState = new revokedState(this);
        archivedState = new archivedState(this);

        state = createdState;
        documentCounter++;

        // 记录创建时间
        addTimestamp("Created");
    }

    // 状态设置，添加时间记录
    public void setState(DocumentState state) {
        this.state = state;
        addTimestamp(state.getStateName());
    }

    // 时间标签管理
    public void addTimestamp(String event) {
        timestamps.put(event, LocalDateTime.now());
    }

    public String getTimestampHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("Time History for Document: ").append(title).append("\n");
        for (Map.Entry<String, LocalDateTime> entry : timestamps.entrySet()) {
            sb.append(String.format("  %s: %s\n",
                    entry.getKey(),
                    entry.getValue().format(formatter)));
        }
        return sb.toString();
    }

    // 状态转换方法
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

    // Getter方法
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
        return "Document: " + title + " | State: " + state.getStateName();
    }
}