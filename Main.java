import java.util.*;

public class Main {
    private static DMSProxy proxy;
    private static Scanner scanner;
    private static Map<String, Observer> userDatabase;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        try {
            // 初始化系统
            initializeSystem();

            // 显示欢迎信息
            showWelcome();

            // 主循环
            boolean running = true;
            while (running) {
                try {
                    // 显示主菜单
                    int mainChoice = showMainMenu();

                    switch (mainChoice) {
                        case 1:
                            // 用户登录
                            loginUser();

                            // 用户操作循环
                            boolean userSessionActive = true;
                            while (userSessionActive) {
                                try {
                                    userSessionActive = showUserMenu();
                                } catch (Exception e) {
                                    System.out.println("\n[Error] " + e.getMessage());
                                    System.out.println("Press Enter to continue...");
                                    scanner.nextLine();
                                }
                            }

                            // 用户注销
                            proxy.logout();
                            break;

                        case 2:
                            // 用户注册
                            registerNewUser();
                            break;

                        case 3:
                            // 查看所有用户
                            viewAllUsers();
                            break;

                        case 0:
                            // 退出系统
                            running = false;
                            break;

                        default:
                            System.out.println("[Error] Invalid choice. Please try again.");
                    }

                } catch (Exception e) {
                    System.out.println("\n[Error] " + e.getMessage());
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                }
            }

            // 退出系统
            System.out.println("\n========================================");
            System.out.println("Thank you for using the Document Management System!");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.out.println("\n[Fatal Error] " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    // 初始化系统
    private static void initializeSystem() {
        System.out.println("Initializing Document Management System...");

        // 创建 DMS
        DMS dms = new DMS();

        // 创建代理
        proxy = new DMSProxy(dms);

        // 创建用户数据库
        userDatabase = new LinkedHashMap<>();

        // 创建并注册默认用户
        createDefaultUsers(dms);

        System.out.println("System initialized successfully!\n");
    }

    // 创建默认用户
    private static void createDefaultUsers(DMS dms) {
        Supervisor supervisor1 = new Supervisor("Alice Wang");
        Supervisor supervisor2 = new Supervisor("Bob Chen");
        Secretary secretary1 = new Secretary("Carol Liu");
        Secretary secretary2 = new Secretary("David Zhang");
        Officer officer1 = new Officer("Emma Li");
        Officer officer2 = new Officer("Frank Wu");
        Archivist archivist1 = new Archivist("Grace Huang");
        Archivist archivist2 = new Archivist("Henry Zhou");

        // 注册到 DMS
        dms.registerSupervisor(supervisor1);
        dms.registerSupervisor(supervisor2);
        dms.registerSecretary(secretary1);
        dms.registerSecretary(secretary2);
        dms.registerOfficer(officer1);
        dms.registerOfficer(officer2);
        dms.registerArchivist(archivist1);
        dms.registerArchivist(archivist2);

        // 添加到用户数据库
        userDatabase.put("alice", supervisor1);
        userDatabase.put("bob", supervisor2);
        userDatabase.put("carol", secretary1);
        userDatabase.put("david", secretary2);
        userDatabase.put("emma", officer1);
        userDatabase.put("frank", officer2);
        userDatabase.put("grace", archivist1);
        userDatabase.put("henry", archivist2);
    }

    // 显示欢迎信息
    private static void showWelcome() {
        System.out.println("========================================");
        System.out.println("  Document Management System (DMS)");
        System.out.println("========================================");
        System.out.println();
    }

    // 显示主菜单
    private static int showMainMenu() {
        System.out.println("\n========== Main Menu ==========");
        System.out.println("1. Login");
        System.out.println("2. Register new user");
        System.out.println("3. View all users");
        System.out.println("0. Exit");
        System.out.println("===============================");
        System.out.print("\nEnter your choice: ");

        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // 用户注册
    private static void registerNewUser() {
        System.out.println("\n========== User Registration ==========");

        try {
            // 输入用户名
            String username;
            while (true) {
                System.out.print("Enter username (lowercase, no spaces): ");
                username = scanner.nextLine().trim().toLowerCase();

                if (username.isEmpty()) {
                    System.out.println("[Error] Username cannot be empty. Please try again.\n");
                    continue;
                }

                if (username.contains(" ")) {
                    System.out.println("[Error] Username cannot contain spaces. Please try again.\n");
                    continue;
                }

                if (userDatabase.containsKey(username)) {
                    System.out.println("[Error] Username already exists. Please choose another one.\n");
                    continue;
                }

                break;
            }

            // 输入真实姓名
            String realName;
            while (true) {
                System.out.print("Enter real name: ");
                realName = scanner.nextLine().trim();

                if (realName.isEmpty()) {
                    System.out.println("[Error] Real name cannot be empty. Please try again.\n");
                    continue;
                }

                break;
            }

            // 选择角色
            String role;
            while (true) {
                System.out.println("\nSelect role:");
                System.out.println("1. Supervisor (项目主管)");
                System.out.println("2. Secretary (秘书)");
                System.out.println("3. Officer (官员)");
                System.out.println("4. Archivist (档案管理员)");
                System.out.print("Enter choice (1-4): ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        role = "Supervisor";
                        break;
                    case "2":
                        role = "Secretary";
                        break;
                    case "3":
                        role = "Officer";
                        break;
                    case "4":
                        role = "Archivist";
                        break;
                    default:
                        System.out.println("[Error] Invalid choice. Please try again.\n");
                        continue;
                }
                break;
            }

            // 创建用户对象
            Observer newUser = createUserByRole(realName, role);

            // 注册到 DMS
            DMS dms = proxy.getDMS();
            registerUserToDMS(newUser, role, dms);

            // 添加到用户数据库
            userDatabase.put(username, newUser);

            System.out.println("\n[Success] User registered successfully!");
            System.out.println("Username: " + username);
            System.out.println("Real Name: " + realName);
            System.out.println("Role: " + role);
            System.out.println("======================================\n");

        } catch (Exception e) {
            System.out.println("\n[Error] Registration failed: " + e.getMessage());
        }
    }

    // 根据角色创建用户对象
    private static Observer createUserByRole(String name, String role) {
        switch (role) {
            case "Supervisor":
                return new Supervisor(name);
            case "Secretary":
                return new Secretary(name);
            case "Officer":
                return new Officer(name);
            case "Archivist":
                return new Archivist(name);
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    // 将用户注册到 DMS
    private static void registerUserToDMS(Observer user, String role, DMS dms) {
        switch (role) {
            case "Supervisor":
                dms.registerSupervisor((Supervisor) user);
                break;
            case "Secretary":
                dms.registerSecretary((Secretary) user);
                break;
            case "Officer":
                dms.registerOfficer((Officer) user);
                break;
            case "Archivist":
                dms.registerArchivist((Archivist) user);
                break;
        }
    }

    // 查看所有用户
    private static void viewAllUsers() {
        System.out.println("\n========== All Registered Users ==========");

        if (userDatabase.isEmpty()) {
            System.out.println("No users registered in the system.");
        } else {
            // 按角色分组显示
            Map<String, List<String>> usersByRole = new LinkedHashMap<>();
            usersByRole.put("Supervisor", new ArrayList<>());
            usersByRole.put("Secretary", new ArrayList<>());
            usersByRole.put("Officer", new ArrayList<>());
            usersByRole.put("Archivist", new ArrayList<>());

            for (Map.Entry<String, Observer> entry : userDatabase.entrySet()) {
                String username = entry.getKey();
                Observer user = entry.getValue();
                String userInfo = String.format("%s (%s)", username, user.getName());
                usersByRole.get(user.getRole()).add(userInfo);
            }

            System.out.println("\nSupervisors (项目主管):");
            displayUserList(usersByRole.get("Supervisor"));

            System.out.println("\nSecretaries (秘书):");
            displayUserList(usersByRole.get("Secretary"));

            System.out.println("\nOfficers (官员):");
            displayUserList(usersByRole.get("Officer"));

            System.out.println("\nArchivists (档案管理员):");
            displayUserList(usersByRole.get("Archivist"));

            System.out.println("\nTotal users: " + userDatabase.size());
        }

        System.out.println("==========================================\n");
    }

    // 显示用户列表
    private static void displayUserList(List<String> users) {
        if (users.isEmpty()) {
            System.out.println("  (None)");
        } else {
            for (String user : users) {
                System.out.println("  - " + user);
            }
        }
    }

    // 用户登录
    private static void loginUser() throws Exception {
        System.out.println("\n========== User Login ==========");

        if (userDatabase.isEmpty()) {
            throw new Exception("No users registered. Please register first.");
        }

        System.out.println("\nTip: Enter '?' to view all users");

        while (true) {
            System.out.print("Enter username (or 'back' to return): ");
            String username = scanner.nextLine().trim().toLowerCase();

            if (username.equals("back")) {
                throw new Exception("User cancelled login.");
            }

            if (username.equals("?")) {
                viewAllUsers();
                continue;
            }

            if (username.isEmpty()) {
                System.out.println("[Error] Username cannot be empty. Please try again.\n");
                continue;
            }

            Observer user = userDatabase.get(username);
            if (user != null) {
                proxy.setCurrentUser(user);
                return;
            } else {
                System.out.println("[Error] User not found. Please try again.\n");
            }
        }
    }

    // 显示用户菜单并处理操作
    private static boolean showUserMenu() throws Exception {
        Observer currentUser = proxy.getCurrentUser();
        String role = currentUser.getRole();

        System.out.println("\n========================================");
        System.out.println("  Welcome, " + currentUser.getName() + " (" + role + ")");
        System.out.println("========================================");

        // 根据角色显示不同的菜单
        switch (role) {
            case "Supervisor":
                return showSupervisorMenu();
            case "Secretary":
                return showSecretaryMenu();
            case "Officer":
                return showOfficerMenu();
            case "Archivist":
                return showArchivistMenu();
            default:
                throw new Exception("Unknown role: " + role);
        }
    }

    // Supervisor 菜单
    private static boolean showSupervisorMenu() throws Exception {
        System.out.println("\n[Supervisor Operations]");
        System.out.println("1. Create new document");
        System.out.println("2. Initiate project");
        System.out.println("3. Review content");
        System.out.println("4. View all documents");
        System.out.println("5. View document details");
        System.out.println("6. View my notifications");
        System.out.println("7. Clear my notifications");
        System.out.println("0. Logout");
        System.out.print("\nEnter your choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                createDocument();
                break;
            case "2":
                initiateProject();
                break;
            case "3":
                reviewContent();
                break;
            case "4":
                viewAllDocuments();
                break;
            case "5":
                viewDocumentDetails();
                break;
            case "6":
                proxy.showMyNotifications();
                break;
            case "7":
                proxy.clearMyNotifications();
                break;
            case "0":
                return false;
            default:
                System.out.println("[Error] Invalid choice. Please try again.");
        }

        return true;
    }

    // Secretary 菜单
    private static boolean showSecretaryMenu() throws Exception {
        System.out.println("\n[Secretary Operations]");
        System.out.println("1. Edit document content");
        System.out.println("2. Submit document for review");
        System.out.println("3. View all documents");
        System.out.println("4. View document details");
        System.out.println("5. View my notifications");
        System.out.println("6. Clear my notifications");
        System.out.println("0. Logout");
        System.out.print("\nEnter your choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                editDocumentContent();
                break;
            case "2":
                submitContent();
                break;
            case "3":
                viewAllDocuments();
                break;
            case "4":
                viewDocumentDetails();
                break;
            case "5":
                proxy.showMyNotifications();
                break;
            case "6":
                proxy.clearMyNotifications();
                break;
            case "0":
                return false;
            default:
                System.out.println("[Error] Invalid choice. Please try again.");
        }

        return true;
    }

    // Officer 菜单
    private static boolean showOfficerMenu() throws Exception {
        System.out.println("\n[Officer Operations]");
        System.out.println("1. Approve document");
        System.out.println("2. Reject document");
        System.out.println("3. Revoke project");
        System.out.println("4. View all documents");
        System.out.println("5. View document details");
        System.out.println("6. View my notifications");
        System.out.println("7. Clear my notifications");
        System.out.println("0. Logout");
        System.out.print("\nEnter your choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                approveDocument(true);
                break;
            case "2":
                approveDocument(false);
                break;
            case "3":
                revokeDocument();
                break;
            case "4":
                viewAllDocuments();
                break;
            case "5":
                viewDocumentDetails();
                break;
            case "6":
                proxy.showMyNotifications();
                break;
            case "7":
                proxy.clearMyNotifications();
                break;
            case "0":
                return false;
            default:
                System.out.println("[Error] Invalid choice. Please try again.");
        }

        return true;
    }

    // Archivist 菜单
    private static boolean showArchivistMenu() throws Exception {
        System.out.println("\n[Archivist Operations]");
        System.out.println("1. Archive document");
        System.out.println("2. View all documents");
        System.out.println("3. View document details");
        System.out.println("4. View my notifications");
        System.out.println("5. Clear my notifications");
        System.out.println("0. Logout");
        System.out.print("\nEnter your choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                archiveDocument();
                break;
            case "2":
                viewAllDocuments();
                break;
            case "3":
                viewDocumentDetails();
                break;
            case "4":
                proxy.showMyNotifications();
                break;
            case "5":
                proxy.clearMyNotifications();
                break;
            case "0":
                return false;
            default:
                System.out.println("[Error] Invalid choice. Please try again.");
        }

        return true;
    }

    // ========== 操作实现方法 ==========

    // 创建文档
    private static void createDocument() throws Exception {
        System.out.println("\n--- Create New Document ---");
        System.out.print("Enter document title: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            throw new Exception("Document title cannot be empty.");
        }

        Document doc = proxy.createDocument(title);
        System.out.println("\n[Success] Document created: " + doc.getTitle());
        System.out.println("Current state: " + doc.getState().getStateName());
    }

    // 立项
    private static void initiateProject() throws Exception {
        Document doc = selectDocument();
        if (doc != null) {
            proxy.initiateProject(doc);
            System.out.println("\n[Success] Project initiated for: " + doc.getTitle());
        }
    }

    // 编辑文档内容
    private static void editDocumentContent() throws Exception {
        Document doc = selectDocument();
        if (doc != null) {
            System.out.println("\nCurrent content:");
            String currentContent = proxy.viewDocumentContent(doc);
            if (currentContent.isEmpty()) {
                System.out.println("(Empty)");
            } else {
                System.out.println(currentContent);
            }

            System.out.println("\nEnter new content (type 'END' on a new line to finish):");
            StringBuilder content = new StringBuilder();
            while (true) {
                String line = scanner.nextLine();
                if (line.equals("END")) {
                    break;
                }
                content.append(line).append("\n");
            }

            proxy.editDocumentContent(doc, content.toString().trim());
            System.out.println("\n[Success] Content updated for: " + doc.getTitle());
        }
    }

    // 提交内容
    private static void submitContent() throws Exception {
        Document doc = selectDocument();
        if (doc != null) {
            proxy.submitContent(doc);
            System.out.println("\n[Success] Content submitted for review: " + doc.getTitle());
        }
    }

    // 审核内容
    private static void reviewContent() throws Exception {
        Document doc = selectDocument();
        if (doc != null) {
            System.out.println("\nDocument content:");
            System.out.println(proxy.viewDocumentContent(doc));

            System.out.print("\nDoes the content pass review? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            boolean passed = choice.equals("y") || choice.equals("yes");

            proxy.reviewContent(doc, passed);
            System.out.println("\n[Success] Review completed for: " + doc.getTitle());
        }
    }

    // 批准文档
    private static void approveDocument(boolean approved) throws Exception {
        Document doc = selectDocument();
        if (doc != null) {
            System.out.println("\nDocument content:");
            System.out.println(proxy.viewDocumentContent(doc));

            proxy.approveContent(doc, approved);
            System.out.println("\n[Success] Document " +
                    (approved ? "approved" : "rejected") + ": " + doc.getTitle());
        }
    }

    // 撤销文档
    private static void revokeDocument() throws Exception {
        Document doc = selectDocument();
        if (doc != null) {
            System.out.print("\nAre you sure you want to revoke this project? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y") || choice.equals("yes")) {
                proxy.revokeDocument(doc);
                System.out.println("\n[Success] Project revoked: " + doc.getTitle());
            } else {
                System.out.println("\n[Cancelled] Revocation cancelled.");
            }
        }
    }

    // 归档文档
    private static void archiveDocument() throws Exception {
        Document doc = selectDocument();
        if (doc != null) {
            proxy.archiveDocument(doc);
            System.out.println("\n[Success] Document archived: " + doc.getTitle());
        }
    }

    // 查看所有文档
    private static void viewAllDocuments() throws Exception {
        proxy.showAllDocumentStatus();
    }

    // 查看文档详情
    private static void viewDocumentDetails() throws Exception {
        Document doc = selectDocument();
        if (doc != null) {
            System.out.println("\n========== Document Details ==========");
            System.out.println("Title: " + doc.getTitle());
            System.out.println("State: " + doc.getState().getStateName());
            System.out.println("\nContent:");
            String content = proxy.viewDocumentContent(doc);
            if (content.isEmpty()) {
                System.out.println("(Empty)");
            } else {
                System.out.println(content);
            }
            System.out.println("\n" + proxy.viewDocumentTimeHistory(doc));
            System.out.println("======================================");
        }
    }

    // 选择文档（辅助方法）
    private static Document selectDocument() throws Exception {
        List<Document> documents = proxy.getDocuments();

        if (documents.isEmpty()) {
            System.out.println("\n[Info] No documents available in the system.");
            return null;
        }

        System.out.println("\n--- Select Document ---");
        for (int i = 0; i < documents.size(); i++) {
            Document doc = documents.get(i);
            System.out.println((i + 1) + ". " + doc.toString());
        }
        System.out.println("0. Cancel");

        while (true) {
            System.out.print("\nEnter document number: ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("[Cancelled] Operation cancelled.");
                return null;
            }

            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < documents.size()) {
                    return documents.get(index);
                } else {
                    System.out.println("[Error] Invalid document number. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error] Please enter a valid number.");
            }
        }
    }
}