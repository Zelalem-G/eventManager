package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import main.eventmanager.SceneController;
import main.eventmanager.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class myAccountController {

    public TabPane accountTabPane;
    @FXML
    private Label AusernameLabel;

    @FXML
    private TextField AfullNameField;

    @FXML
    private TextField AemailField;

    @FXML
    private Label AstatusLabel;

    @FXML
    private Button AupdateBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private PasswordField AcurrentPassword;

    @FXML
    private PasswordField AnewPassword;

    @FXML
    private PasswordField AconfirmPassword;

    @FXML
    private Label ApasswordStatus;

    @FXML
    public void initialize() {
        int userId = Session.getUserId();

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT username, full_name, email FROM users WHERE user_id = ?")) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                AusernameLabel.setText(rs.getString("username"));
                AfullNameField.setText(rs.getString("full_name"));
                AemailField.setText(rs.getString("email"));
            }

        } catch (SQLException e) {
            showError("Failed to load account info.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        int userId = Session.getUserId();
        String fullName = AfullNameField.getText().trim();
        String email = AemailField.getText().trim();

        if (fullName.isEmpty() || email.isEmpty()) {
            showError("Full name and email cannot be empty.");
            return;
        }

        String sql = "UPDATE users SET full_name = ?, email = ? WHERE user_id = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setInt(3, userId);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                showSuccess("Profile updated successfully.");
            } else {
                showError("No changes were made.");
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                showError("Email already exists.");
            } else {
                showError("Error updating profile.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleChangePassword(ActionEvent event) {
        int userId = Session.getUserId();
        String currentPassword = AcurrentPassword.getText().trim();
        String newPassword = AnewPassword.getText().trim();
        String confirmPassword = AconfirmPassword.getText().trim();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            setPasswordStatus("All fields are required.", false);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            setPasswordStatus("New passwords do not match.", false);
            return;
        }

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE user_id = ?")) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String existingPassword = rs.getString("password");

                if (!existingPassword.equals(currentPassword)) { // Simplified; normally use hashing
                    setPasswordStatus("Current password is incorrect.", false);
                    return;
                }

                try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE user_id = ?")) {
                    updateStmt.setString(1, newPassword); // Normally, you should hash this
                    updateStmt.setInt(2, userId);
                    updateStmt.executeUpdate();
                    setPasswordStatus("Password changed successfully.", true);
                    AcurrentPassword.clear();
                    AnewPassword.clear();
                    AconfirmPassword.clear();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            setPasswordStatus("An error occurred while changing password.", false);
        }
    }

    private void setPasswordStatus(String message, boolean success) {
        ApasswordStatus.setText(message);
        ApasswordStatus.setStyle(success ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    @FXML
    private void logout(ActionEvent event) {
        // Clear the user session data
        Session.clearUserData(); // Assuming you have a method like this in the Session class

        // Change scene to the login page
        SceneController.changeScene(event, "login.fxml", "Login", "", "");
    }


    @FXML
    private void handleBack(ActionEvent event) {
        SceneController.changeScene(event, "home.fxml", "Home", Session.getUsername(), Session.getRole());
    }

    private void showError(String message) {
        AstatusLabel.setText(message);
        AstatusLabel.setStyle("-fx-text-fill: #FF3B3B;");
    }

    private void showSuccess(String message) {
        AstatusLabel.setText(message);
        AstatusLabel.setStyle("-fx-text-fill: #AAFF00;");
    }
}
