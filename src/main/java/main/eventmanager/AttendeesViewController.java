package main.eventmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Attendee;
import model.Database;
import model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendeesViewController {

    @FXML
    private ComboBox<Event> eventComboBox;

    @FXML
    private TableView<Attendee> attendeesTable;

    @FXML
    private TableColumn<Attendee, String> nameCol;

    @FXML
    private TableColumn<Attendee, String> emailCol;

    @FXML
    private TableColumn<Attendee, String> phoneCol;

    @FXML
    private TableColumn<Attendee, String> genderCol;

    @FXML
    private TableColumn<Attendee, String> institutionCol;

    @FXML
    private TableColumn<Attendee, Integer> ageCol;

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        institutionCol.setCellValueFactory(new PropertyValueFactory<>("institution"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        loadEvents();
    }

    private void loadEvents() {
        List<Event> events = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT event_id, event_name FROM events")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(new Event(rs.getInt("event_id"), rs.getString("event_name")));
            }
            eventComboBox.setItems(FXCollections.observableArrayList(events));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEventSelect(ActionEvent event) {
        Event selected = eventComboBox.getSelectionModel().getSelectedItem();
        if (selected != null) {
            loadAttendees(selected.getId());
        }
    }

    private void loadAttendees(int eventId) {
        ObservableList<Attendee> attendees = FXCollections.observableArrayList();
        String sql = """
                SELECT u.full_name, a.email, a.phone_number, a.gender, a.institution, a.age
                FROM registrations r
                JOIN users u ON r.user_id = u.user_id
                JOIN attendees a ON r.user_id = a.user_id
                WHERE r.event_id = ?
                """;
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attendee attendee = new Attendee(
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("gender"),
                        rs.getString("institution"),
                        rs.getInt("age")
                );
                attendees.add(attendee);
            }
            attendeesTable.setItems(attendees);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Navigation handlers
    @FXML
    private void goToUsers(ActionEvent event) {
        SceneController.changeScene(event, "users_view.fxml", "Users", Session.getUsername(), Session.getRole());
    }

    @FXML
    private void goToFeedback(ActionEvent event) {
        SceneController.changeScene(event, "feedback_view.fxml", "Feedback", Session.getUsername(), Session.getRole());
    }

    @FXML
    private void goToStatistics(ActionEvent event) {
        SceneController.changeScene(event, "statistics_view.fxml", "Statistics", Session.getUsername(), Session.getRole());
    }
}
