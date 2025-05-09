package model;

public class User {
    private int userId;
    private String username;
    private String fullName;
    private String email;
    private String role;

    public User(int userId, String username, String fullName, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    // Getters and setters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}

