import java.util.*;

public class DMS{
    private static final int MAX_DOCUMENTS = 10;

    private List<Document> documents;
    private List<Observer> observers;

    // 四类用户
    private Set<Supervisor> supervisors = new LinkedHashSet<>();
    private Set<Secretary> secretaries = new LinkedHashSet<>();
    private Set<Officer> officers = new LinkedHashSet<>();
    private Set<Archivist> archivists = new LinkedHashSet<>();

    public DMS() {
        this.documents = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // 注册用户（支持同类多人）
    public void registerSupervisor(Supervisor supervisor) {
        if (supervisor == null) return;
        supervisors.add(supervisor);
        attachObserver(supervisor);
    }

    public void registerSecretary(Secretary secretary) {
        if (secretary == null) return;
        secretaries.add(secretary);
        attachObserver(secretary);
    }

    public void registerOfficer(Officer officer) {
        if (officer == null) return;
        officers.add(officer);
        attachObserver(officer);
    }

    public void registerArchivist(Archivist archivist) {
        if (archivist == null) return;
        archivists.add(archivist);
        attachObserver(archivist);
    }

    // 观察者管理
    public void attachObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void detachObserver(Observer observer) {
        observers.remove(observer);
    }

    // 选择性通知特定角色的观察者（改为静默通知）
    private void notifySpecificObservers(Document doc, String message, String... roles) {
        // roles是一个String数组，包含需要通知的角色名称
        Set<String> roleSet = new HashSet<>(Arrays.asList(roles));
        for (Observer observer : observers) {
            if (roleSet.contains(observer.getRole())) {
                observer.update(doc, message);  // 静默添加到通知列表
            }
        }
    }

    // 通知所有观察者（改为静默通知）
    private void notifyAllObservers(Document doc, String message) {
        for (Observer observer : observers) {
            observer.update(doc, message);  // 静默添加到通知列表
        }
    }

    // 文档管理方法
    public Document createDocument(String title) throws Exception {
        if (documents.size() >= MAX_DOCUMENTS) {
            throw new Exception("Cannot create document. Maximum limit of " + MAX_DOCUMENTS + " documents reached.");
        }

        Document doc = new Document(title);
        documents.add(doc);
        System.out.println("Document '" + title + "' created successfully.");
        return doc;
    }

    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    public List<Document> getDocuments() {
        return new ArrayList<>(documents);
    }

    public Document findDocumentByTitle(String title) {
        for (Document doc : documents) {
            if (doc.getTitle().equalsIgnoreCase(title)) {
                return doc;
            }
        }
        return null;
    }

    // 业务操作方法（带通知）
    public void initiateProject(Document doc) {
        String previousState = doc.getState().getStateName();
        doc.initiateProject();

        // 只有状态真正改变时才通知
        if (!previousState.equals(doc.getState().getStateName())) {
            // 系统自动通知官员（Officer）和秘书（Secretary）
            notifySpecificObservers(doc,
                    "Project '" + doc.getTitle() + "' has been initiated. Waiting for content drafting.",
                    "Officer", "Secretary");
        }
    }

    public void submitContent(Document doc) {
        String previousState = doc.getState().getStateName();
        doc.submitContent();

        if (!previousState.equals(doc.getState().getStateName())) {
            // 系统自动通知官员（Officer）和项目主管（Supervisor）
            notifySpecificObservers(doc,
                    "Content of '" + doc.getTitle() + "' has been submitted for review.",
                    "Officer", "Supervisor");
        }
    }

    public void reviewContent(Document doc, boolean passed) {
        String previousState = doc.getState().getStateName();
        doc.reviewContent(passed);

        if (!previousState.equals(doc.getState().getStateName())) {
            if (passed) {
                // 若审核结果为通过，系统自动通知官员（Officer）
                notifySpecificObservers(doc,
                        "Content of '" + doc.getTitle() + "' has passed review. Waiting for approval.",
                        "Officer");
            } else {
                // 若审核结果为不通过，系统自动通知官员（Officer）和秘书（Secretary）
                notifySpecificObservers(doc,
                        "Content of '" + doc.getTitle() + "' failed review. Returned to writing state for revision.",
                        "Officer", "Secretary");
            }
        }
    }

    public void approveContent(Document doc, boolean approved) {
        String previousState = doc.getState().getStateName();
        doc.approveContent(approved);

        if (!previousState.equals(doc.getState().getStateName())) {
            if (approved) {
                // 若审查结果为批准，系统自动通知项目主管（Supervisor）和档案管理员（Archivist）
                notifySpecificObservers(doc,
                        "Document '" + doc.getTitle() + "' has been approved and published.",
                        "Supervisor", "Archivist");
            } else {
                // 若审查结果为不批准，系统自动通知项目主管（Supervisor）和秘书（Secretary）
                notifySpecificObservers(doc,
                        "Document '" + doc.getTitle() + "' approval rejected. Returned to writing state for revision.",
                        "Supervisor", "Secretary");
            }
        }
    }

    public void revokeDocument(Document doc) {
        String previousState = doc.getState().getStateName();
        doc.revokeDocument();

        if (!previousState.equals(doc.getState().getStateName())) {
            // 若审查结果为撤销项目，系统自动通知项目主管（Supervisor）、秘书（Secretary）和档案管理员（Archivist）
            notifySpecificObservers(doc,
                    "Project '" + doc.getTitle() + "' has been revoked.",
                    "Supervisor", "Secretary", "Archivist");
        }
    }

    public void archiveDocument(Document doc) {
        String previousState = doc.getState().getStateName();
        doc.archiveDocument();

        if (!previousState.equals(doc.getState().getStateName())) {
            // 通知所有相关人员文档已归档
            notifyAllObservers(doc, "Document '" + doc.getTitle() + "' has been archived.");
        }
    }

    // 查询所有文档状态
    public void showAllDocumentStatus() {
        System.out.println("\n========== All Documents Status ==========");
        if (documents.isEmpty()) {
            System.out.println("No documents in the system.");
        } else {
            for (int i = 0; i < documents.size(); i++) {
                Document doc = documents.get(i);
                System.out.println((i + 1) + ". " + doc.toString());
            }
        }
        System.out.println("Total: " + documents.size() + "/" + MAX_DOCUMENTS + " documents");
        System.out.println("==========================================\n");
    }

    // Getter方法
    public Set<Supervisor> getSupervisor() {
        return supervisors;
    }

    public Set<Secretary> getSecretary() {
        return secretaries;
    }

    public Set<Officer> getOfficer() {
        return officers;
    }

    public Set<Archivist> getArchivist() {
        return archivists;
    }
}