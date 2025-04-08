package main.eventmanager;

public class Session {
    private static String username;
    private static int userId;
    private static String role;

    public static void setSession(String user, String r) {
        username = user;
//        userId = id;
        role = r;
    }

    public static String getUsername() { return username; }
    public static int getUserId() { return userId; }
    public static String getRole() { return role; }
}
