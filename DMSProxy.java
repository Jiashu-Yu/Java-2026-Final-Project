public class DMSProxy {
    private DMS dms;
    private Observer currentUser;

    public DMSProxy(DMS dms) {
        this.dms = dms;
        this.currentUser = null;
    }

    // 设置当前登录用户
    public void setCurrentUser(Observer user) {
        this.currentUser = user;
        System.out.println("\n[System] User logged in: " + user.getName() + " (" + user.getRole() + ")");
    }

    // 获取当前用户
    public Observer getCurrentUser() {
        return currentUser;
    }

    // 注销当前用户
    public void logout() {
        if (currentUser != null) {
            System.out.println("\n[System] User logged out: " + currentUser.getName());
            currentUser = null;
        }
    }

    // 检查是否有用户登录
    private void checkLogin() throws Exception {
        if (currentUser == null) {
            throw new Exception("Access denied: No user logged in.");
        }
    }

    // 检查用户角色权限
    private void checkRole(String... allowedRoles) throws Exception {
        checkLogin();
        String userRole = currentUser.getRole();
        for (String role : allowedRoles) {
            if (userRole.equals(role)) {
                return;
            }
        }
        throw new Exception("Access denied: " + userRole + " is not authorized to perform this operation.");
    }

    // ========== 文档创建方法 ==========
    // 只有 Supervisor 可以创建文档
    public Document createDocument(String title) throws Exception {
        checkRole("Supervisor");

        if (title == null || title.trim().isEmpty()) {
            throw new Exception("Document title cannot be empty.");
        }

        return dms.createDocument(title);
    }

    // ========== 项目立项 ==========
    // 只有 Supervisor 可以立项
    public void initiateProject(Document doc) throws Exception {
        checkRole("Supervisor");

        if (doc == null) {
            throw new Exception("Document cannot be null.");
        }

        dms.initiateProject(doc);
    }

    // ========== 提交内容 ==========
    // 只有 Secretary 可以提交内容
    public void submitContent(Document doc) throws Exception {
        checkRole("Secretary");

        if (doc == null) {
            throw new Exception("Document cannot be null.");
        }

        if (doc.getContent() == null || doc.getContent().trim().isEmpty()) {
            throw new Exception("Cannot submit document with empty content.");
        }

        dms.submitContent(doc);
    }

    // ========== 审核内容 ==========
    // 只有 Supervisor 可以审核
    public void reviewContent(Document doc, boolean passed) throws Exception {
        checkRole("Supervisor");

        if (doc == null) {
            throw new Exception("Document cannot be null.");
        }

        dms.reviewContent(doc, passed);
    }

    // ========== 批准内容 ==========
    // 只有 Officer 可以批准
    public void approveContent(Document doc, boolean approved) throws Exception {
        checkRole("Officer");

        if (doc == null) {
            throw new Exception("Document cannot be null.");
        }

        dms.approveContent(doc, approved);
    }

    // ========== 撤销文档 ==========
    // 只有 Officer 可以撤销
    public void revokeDocument(Document doc) throws Exception {
        checkRole("Officer");

        if (doc == null) {
            throw new Exception("Document cannot be null.");
        }

        dms.revokeDocument(doc);
    }

    // ========== 归档文档 ==========
    // 只有 Archivist 可以归档
    public void archiveDocument(Document doc) throws Exception {
        checkRole("Archivist");

        if (doc == null) {
            throw new Exception("Document cannot be null.");
        }

        dms.archiveDocument(doc);
    }

    // ========== 编辑文档内容 ==========
    // 只有 Secretary 可以编辑内容
    public void editDocumentContent(Document doc, String content) throws Exception {
        checkRole("Secretary");

        if (doc == null) {
            throw new Exception("Document cannot be null.");
        }

        if (content == null) {
            throw new Exception("Content cannot be null.");
        }

        // 只能在 Writing 状态下编辑
        if (!doc.getState().getStateName().equals("Writing")) {
            throw new Exception("Can only edit document content in Writing state. Current state: "
                    + doc.getState().getStateName());
        }

        doc.setContent(content);
        System.out.println("Document content updated successfully.");
    }

    // ========== 查看文档内容 ==========
    // 所有用户都可以查看文档内容
    public String viewDocumentContent(Document doc) throws Exception {
        checkLogin();

        if (doc == null) {
            throw new Exception("Document cannot be null.");
        }

        return doc.getContent();
    }

    // ========== 查看文档历史时间戳 ==========
    // 所有用户都可以查看历史时间戳
    public String viewDocumentTimeHistory(Document doc) throws Exception {
        checkLogin();

        if (doc == null) {
            throw new Exception("Document cannot be null.");
        }

        return doc.getTimestampHistory();
    }

    // ========== 查询所有文档状态 ==========
    // 所有用户都可以查询
    public void showAllDocumentStatus() throws Exception {
        checkLogin();
        dms.showAllDocumentStatus();
    }

    // ========== 查找文档 ==========
    // 所有用户都可以查找
    public Document findDocumentByTitle(String title) throws Exception {
        checkLogin();

        if (title == null || title.trim().isEmpty()) {
            throw new Exception("Document title cannot be empty.");
        }

        Document doc = dms.findDocumentByTitle(title);
        if (doc == null) {
            throw new Exception("Document not found: " + title);
        }
        return doc;
    }

    // ========== 获取所有文档列表 ==========
    // 所有用户都可以获取
    public java.util.List<Document> getDocuments() throws Exception {
        checkLogin();
        return dms.getDocuments();
    }

    // ========== 查看当前用户的通知 ==========
    public void showMyNotifications() throws Exception {
        checkLogin();

        if (currentUser instanceof Supervisor) {
            ((Supervisor) currentUser).showNotifications();
        } else if (currentUser instanceof Secretary) {
            ((Secretary) currentUser).showNotifications();
        } else if (currentUser instanceof Officer) {
            ((Officer) currentUser).showNotifications();
        } else if (currentUser instanceof Archivist) {
            ((Archivist) currentUser).showNotifications();
        }
    }

    // ========== 清除当前用户的通知 ==========
    public void clearMyNotifications() throws Exception {
        checkLogin();

        if (currentUser instanceof Supervisor) {
            ((Supervisor) currentUser).clearNotifications();
        } else if (currentUser instanceof Secretary) {
            ((Secretary) currentUser).clearNotifications();
        } else if (currentUser instanceof Officer) {
            ((Officer) currentUser).clearNotifications();
        } else if (currentUser instanceof Archivist) {
            ((Archivist) currentUser).clearNotifications();
        }

        System.out.println("Notifications cleared.");
    }

    // ========== 获取底层 DMS 对象（仅供初始化使用）==========
    public DMS getDMS() {
        return dms;
    }

    // ========== 显示当前用户信息 ==========
    public void showCurrentUserInfo() throws Exception {
        checkLogin();
        System.out.println("\n========== Current User Info ==========");
        System.out.println("Name: " + currentUser.getName());
        System.out.println("Role: " + currentUser.getRole());
        System.out.println("======================================\n");
    }
}