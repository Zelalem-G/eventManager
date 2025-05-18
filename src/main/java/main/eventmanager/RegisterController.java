package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.*;

public class RegisterController {

    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField institutionField;
    @FXML
    private TextField ageField;
    @FXML
    private Button registerButton;
    @FXML
    private Text statusText;

    private int eventId;

    public void initialize() {
        // Populate gender options (Male, Female)
//        genderComboBox.getItems().addAll("Male", "Female");

        // Get the event ID from the session
        this.eventId = Session.getEventId();
    }
    @FXML
    private void goBack(ActionEvent event){
        SceneController.changeScene(event,"events.fxml","events", Session.getUsername(),Session.getRole());

    }

    @FXML
    private void handleRegistration() {
        if (isAnyFieldEmpty()) {
            showError("All fields must be filled in!");
            return;
        }

        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        String gender = genderComboBox.getValue();
        String institution = institutionField.getText();
        int age;

        // Validate formats
        if (!phoneNumber.matches("^(?:\\+251|0)(7\\d{8}|9\\d{8})$")) {
            showError("Invalid phone number! Use format: 07XXXXXXXX, 09XXXXXXXX, +2519XXXXXXXX,or +2517XXXXXXXX");
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            showError("Invalid email address format!");
            return;
        }

        try {
            age = Integer.parseInt(ageField.getText());
            if(age < 1){
                showError("Age must be a valid number!");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Age must be a valid number!");
            return;
        }

        // Get user_id from the session
        int userId = Session.getUserId();

        if (isUserAlreadyRegistered(userId, eventId)) {
            statusText.setText("You are already registered for this event.");
            return;
        }

        // SQL queries to insert into the attendees and registrations tables
        String insertAttendeeSQL = "INSERT INTO attendees (user_id, phone_number, email, gender, institution, age) VALUES (?, ?, ?, ?, ?, ?)";
        String insertRegistrationSQL = "INSERT INTO registrations (user_id, event_id) VALUES (?, ?)";

        try (Connection conn = DBUtils.getConnection()) {
            // Insert the attendee data
            try (PreparedStatement attendeeStmt = conn.prepareStatement(insertAttendeeSQL)) {
                attendeeStmt.setInt(1, userId);
                attendeeStmt.setString(2, phoneNumber);
                attendeeStmt.setString(3, email);
                attendeeStmt.setString(4, gender);
                attendeeStmt.setString(5, institution);
                attendeeStmt.setInt(6, age);

                int attendeeRowsAffected = attendeeStmt.executeUpdate();
                System.out.println("Attendee registered, affected rows: " + attendeeRowsAffected);

                if (attendeeRowsAffected > 0) {
                    // Insert into the registrations table
                    try (PreparedStatement registrationStmt = conn.prepareStatement(insertRegistrationSQL)) {
                        registrationStmt.setInt(1, userId);
                        registrationStmt.setInt(2, eventId);

                        int registrationRowsAffected = registrationStmt.executeUpdate();
                        System.out.println("User registered for event, affected rows: " + registrationRowsAffected);

                        if (registrationRowsAffected > 0) {
                            // Success message
                            showSuccess("Registration successful!");
                        } else {
                            showError("Error registering user for the event.");
                        }
                    }
                } else {
                    showError("Error registering attendee.");
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR IN RegisterController: SQL exception occurred");
            e.printStackTrace();
            showError("Database error occurred. Please try again later.");
        }
    }

    private boolean isAnyFieldEmpty() {
        // Check if any field is empty
        return phoneNumberField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                genderComboBox.getValue() == null ||
                institutionField.getText().isEmpty() ||
                ageField.getText().isEmpty();
    }

    private void showError(String message) {
        // Display error message in the UI
        statusText.setText(message);
        statusText.setFill(Color.web("#FF4C4C"));
//        statusText.setStyle("-fx-text-fill: red;");

    }

    private void showSuccess(String message) {
        // Display success message in the UI
        statusText.setText(message);
        statusText.setFill(Color.web("#28A745"));
//        statusText.setStyle("-fx-text-fill: green;");

        // Show alert dialog
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Registration Successful");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to check if the user is already registered for the event
    private boolean isUserAlreadyRegistered(int userId, int eventId) {
        String checkQuery = "SELECT COUNT(*) FROM registrations WHERE user_id = ? AND event_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkQuery)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // If count is greater than 0, user is already registered
            }
        } catch (SQLException e) {
            System.out.println("Error checking registration: " + e.getMessage());
        }
        return false;
    }
}
