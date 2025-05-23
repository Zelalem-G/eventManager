package main.eventmanager;

public class Session {
    private static String username;
    private static int userId = -1;
    private static int eventId = -1;
    private static String role;

    public static void setSession(String user, String r) {
        username = user;
//        userId = id;
        role = r;
    }
    public static void setSessionId(int id) {
        userId = id;
    }
    public static void setSessionEventId(int id) {
        eventId = id;
    }

    public static void clearUserData() {
        username = null;
        userId = -1;
        eventId = -1;
        role = null;
    }

    public static String getUsername() { return username; }
    public static int getUserId() { return userId; }
    public static String getRole() { return role; }
    public static int getEventId(){ return eventId; }
}
