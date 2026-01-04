public interface DocumentState {
    void initiateProject(Document doc);
    void submitContent(Document doc);
    void reviewContent(Document doc, boolean passed);
    void approveContent(Document doc, boolean approved);
    void revokeDocument(Document doc);
    void archiveDocument(Document doc);

    String getStateName();
}