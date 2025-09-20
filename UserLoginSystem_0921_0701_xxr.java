// 代码生成时间: 2025-09-21 07:01:59
public class UserLoginSystem {

    // 用户信息，用于验证
    private static Map<String, String> userDatabase = new HashMap<>();

    // 初始化用户数据库
    static {
        userDatabase.put("user1", "password1");
        userDatabase.put("user2", "password2");
    }

    public static boolean login(String username, String password) {
        // 检查用户名是否在数据库中
        if (!userDatabase.containsKey(username)) {
            System.out.println("Username not found.");
            return false;
        }

        // 检查密码是否匹配
        String storedPassword = userDatabase.get(username);
        if (!storedPassword.equals(password)) {
            System.out.println("Incorrect password.");
            return false;
        }

        System.out.println("Login successful for user: " + username);
        return true;
    }

    public static void main(String[] args) {
        // 测试登录系统
        boolean loginStatus = login("user1", "password1");
        if (loginStatus) {
            System.out.println("User logged in successfully.");
        } else {
            System.out.println("Login failed.");
        }
    }
}
