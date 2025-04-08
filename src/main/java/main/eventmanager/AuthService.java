package main.eventmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    public static boolean registerUser(String username, String password) {
        // insert into database
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // You can hash it before storing
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Improve with user-friendly message
            return false;
        }
    }
    }

    public static boolean authenticate(String username, String password) {
        // check from database
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // Compare hashed if hashed
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getUserId(String username) {
        // return user ID
        return Session.getUserId();
    }

    public static String getUserRole(String username) {
        // optional: return user role
        return Session.ge()
    }
}
