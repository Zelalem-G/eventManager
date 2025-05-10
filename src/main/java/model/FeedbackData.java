package model;

public class FeedbackData {
    private String username;
    private int rating;
    private String message;
    private String createdAt; // Keep as String for compatibility

    public FeedbackData(String username, int rating, String message, String createdAt) {
        this.username = username;
        this.rating = rating;
        this.message = message;
        this.createdAt = createdAt;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}

