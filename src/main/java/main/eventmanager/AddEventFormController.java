package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddEventFormController {
    @FXML
    private Label registerResultLabel;
    @FXML
    private TextField eventNameTextfield;
    @FXML
    private TextField locationTextfield;
    @FXML
    private TextField imagePathTextfield;
    @FXML
    private DatePicker eventDatePicker;
    @FXML
    private TextArea eventDescriptionTextArea;

    private int adminId = Session.getUserId();

    @FXML
    private void onBack(ActionEvent event){
        SceneController.changeScene(event,"home.fxml","events", Session.getUsername(),Session.getRole());

    }

    public void onRegisterEvent(ActionEvent event){
        String eventName = eventNameTextfield.getText();
        String location = locationTextfield.getText();
        String imagePath = imagePathTextfield.getText();
        String description = eventDescriptionTextArea.getText().trim();
        LocalDate selectedDate = eventDatePicker.getValue();


        if (eventName.isEmpty() || location.isEmpty() || imagePath.isEmpty() || description.isEmpty() || selectedDate == null) {
            registerResultLabel.setTextFill(Color.web("#FF4C4C"));
            registerResultLabel.setText("Please fill in all fields.");
            return;
        }
        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);

        System.out.println("on register " + adminId);

        String insertQuery = "INSERT INTO events (event_name, description, location, event_date, image_path, created_by) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection()){
            // 1. Check if event already exist
                if (isEventAlreadyRegistered(conn, eventName, sqlDate, adminId)) {
                    // event already exists
                    registerResultLabel.setText("Event already registered! try a different event!");
                    registerResultLabel.setTextFill(Color.web("#FF4C4C"));
                    return;
                }

            // 2. Insert new event
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

                insertStmt.setString(1, eventName);
                insertStmt.setString(2, description);
                insertStmt.setString(3, location);
                insertStmt.setDate(4, sqlDate);
                insertStmt.setString(5, imagePath);
                insertStmt.setInt(6, adminId);

                int rowsInserted = insertStmt.executeUpdate();

                // an error occurred
                if(rowsInserted <= 0){
                    System.out.println("an error occurred. no inserted row");
                    registerResultLabel.setText("An error occurred. Please try again.");
                    registerResultLabel.setTextFill(Color.web("#FF4C4C"));
                    return;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            registerResultLabel.setText("An error occurred. Please try again.");
            registerResultLabel.setTextFill(Color.web("#FF4C4C"));
            return ;
        }
        registerResultLabel.setTextFill(Color.web("#28A745"));
        registerResultLabel.setText("Event Successfully registered");
    }

    public boolean isEventAlreadyRegistered(Connection conn, String eventName, java.sql.Date eventDate, int adminId) throws SQLException {
        String sql = "SELECT * FROM events WHERE event_name = ? AND event_date = ? AND created_by = ?";

        System.out.println("is already registered: " + adminId);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventName);
            stmt.setDate(2,eventDate);
            stmt.setInt(3, adminId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // If there's a row, the event is already registered by this admin
            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new SQLException("Failed to check if event is already registered", e);
        }

    }
}
