package main.eventmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {

    public enum LoginResult {
        SUCCESS,
        USER_NOT_FOUND,
        WRONG_PASSWORD,
        ERROR
    }
    public enum SignupResult {
        SUCCESS,
        USERNAME_TAKEN,
        ERROR
    }

    public static SignupResult registerUser(String username, String password,String email , String full_name , String role) {
        // INSERT user into DB
        String checkUsernameQuery = "SELECT * FROM users WHERE username = ?";
        String insertQuery = "INSERT INTO users (username, password, full_name , email, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection()){
            // 1. Check if username already exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUsernameQuery)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Username already exists
                    return SignupResult.USERNAME_TAKEN;
                }
            }

            // 2. Insert new user
             try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

                 insertStmt.setString(1, username);
                 insertStmt.setString(2, password); // You can hash this later for security
                 insertStmt.setString(3, full_name);
                 insertStmt.setString(4, email);
                 insertStmt.setString(5, role);

                 int rowsInserted = insertStmt.executeUpdate();
                 return rowsInserted > 0 ? SignupResult.SUCCESS : SignupResult.ERROR;
             }

        } catch (SQLException e) {
            e.printStackTrace();
            return SignupResult.ERROR;
        }
    }

    public static LoginResult authenticate(String username, String password) {
        // SELECT from DB to verify credentials
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return LoginResult.USER_NOT_FOUND;
            }

            String storedPassword = rs.getString("password");

            if (!storedPassword.equals(password)) {
                return LoginResult.WRONG_PASSWORD;
            }
//            int id = rs.getInt("user_id");
            String role = rs.getString("role");

            Session.setSession(username, role);
            return LoginResult.SUCCESS;

        } catch (SQLException e) {
            System.out.println("Login SQL Error: " + e.getMessage());
            e.printStackTrace();
            return LoginResult.ERROR;
        }
    }

    public static int getUserId(String username) {
        // SELECT user_id FROM DB
        String query = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static String getUserRole(String username) {
        // SELECT role FROM DB
        String query = "SELECT role FROM users WHERE username = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getUserID(String username) {
        // SELECT role FROM DB
        String query = "SELECT user_id FROM users WHERE username = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("user_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getUsernameById(int id) {
        String query = "SELECT username FROM users WHERE id = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
