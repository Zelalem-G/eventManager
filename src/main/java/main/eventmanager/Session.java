package main.eventmanager;

public class Session {
    private static String username;
    private static int userId;

    public static void setSession(String u, int id) {
        username = u;
        userId = id;
    }

    public static String getUsername() { return username; }
    public static int getUserId() { return userId; }
}
