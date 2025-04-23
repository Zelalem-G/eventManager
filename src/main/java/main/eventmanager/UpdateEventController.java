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

public class UpdateEventController {
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
    private int eventId = Session.getEventId();  //     the event id must be set on the event list of the admin

    public void setTextfieldDefaultValues(){

    }

    public void onUpdateEvent(ActionEvent event){
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

        System.out.println("on update " + adminId);

        String updateQuery = "UPDATE events SET event_name = ?, description = ?, location = ?, event_date = ?, image_path = ?, created_by = ?, WHERE event_id = ?";

        try (Connection conn = DBUtils.getConnection()){
            // 1. Check if event already exist
            if (isEventAlreadyUpdated(conn, eventName, description, location, sqlDate, imagePath, adminId)) {
                // event already exists
                registerResultLabel.setText("Event already registered! try a different event!");
                registerResultLabel.setTextFill(Color.web("#FF4C4C"));
                return;
            }

            // 2. Insert the updated event
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

                updateStmt.setString(1, eventName);
                updateStmt.setString(2, description);
                updateStmt.setString(3, location);
                updateStmt.setDate(4, sqlDate);
                updateStmt.setString(5, imagePath);
                updateStmt.setInt(6, adminId);
                updateStmt.setInt(7, eventId);

                int rowsInserted = updateStmt.executeUpdate();

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

    public boolean isEventAlreadyUpdated(Connection conn, String eventName, String description, String location, java.sql.Date eventDate, String imagePath, int adminId) throws SQLException {
        String sql = "SELECT * FROM events WHERE event_name = ? AND description = ? AND location = ? AND event_date = ? AND image_path = ? AND created_by = ?";

        System.out.println("is already registered: " + adminId);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, eventName);
            stmt.setString(2, description);
            stmt.setString(3, location);
            stmt.setDate(4, eventDate);
            stmt.setString(5, imagePath);
            stmt.setInt(6, adminId);

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
