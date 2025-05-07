package main.eventmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EventCRUDController implements Initializable {

    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button updateButton;

    @FXML private TextField eventNameTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField imagePathTextField;
    @FXML private TextField descriptionTextField;
    @FXML private DatePicker eventDatePicker;

    @FXML private TableView<Events> table;
    @FXML private TableColumn<Events, String> idCol;
    @FXML private TableColumn<Events, String> eventNameCol;
    @FXML private TableColumn<Events, String> locationCol;
    @FXML private TableColumn<Events, String> eventDateCol;
    @FXML private TableColumn<Events, String> imagePathCol;
    @FXML private TableColumn<Events, String> descriptionCol;

    private Connection connection;
    private int selectedId = -1;

    @FXML
    private Label welcomeLabel;

    @FXML
    private MenuItem adminEventBtn;

    @FXML
    private MenuItem adminOpsBtn;

    private MenuControllerHelper menuHelper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuHelper = new MenuControllerHelper(welcomeLabel, adminEventBtn, adminOpsBtn);
        menuHelper.initializeMenu(Session.getUsername(), Session.getRole());
        try {
            connection = DBUtils.getConnection();
            setupTable();
            loadTableData();
            setupRowClickListener();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not connect to the database.");
        }
    }

    @FXML
    private void onMenuClick(ActionEvent event) {
        if (menuHelper != null) {
            menuHelper.handleMenuClick(event);
        }
    }

    private void setupTable() {
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        eventNameCol.setCellValueFactory(cellData -> cellData.getValue().eventNameProperty());
        locationCol.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        eventDateCol.setCellValueFactory(cellData -> cellData.getValue().eventDateProperty());
        imagePathCol.setCellValueFactory(cellData -> cellData.getValue().imagePathProperty());
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    private void loadTableData() {
        ObservableList<Events> eventList = FXCollections.observableArrayList();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM events")) {

            while (rs.next()) {
                eventList.add(new Events(
                        String.valueOf(rs.getInt("event_id")),
                        rs.getString("event_name"),
                        rs.getString("location"),
                        rs.getDate("event_date").toString(),
                        rs.getString("image_path"),
                        rs.getString("description")
                ));
            }

            table.setItems(eventList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Load Error", "Failed to load events: " + e.getMessage());
        }
    }

    private void setupRowClickListener() {
        table.setOnMouseClicked(event -> {
            Events selectedEvent = table.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                selectedId = Integer.parseInt(selectedEvent.getId());
                eventNameTextField.setText(selectedEvent.getEventName());
                locationTextField.setText(selectedEvent.getLocation());
                eventDatePicker.setValue(LocalDate.parse(selectedEvent.getEventDate()));
                imagePathTextField.setText(selectedEvent.getImagePath());
                descriptionTextField.setText(selectedEvent.getDescription());
            }
        });
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        System.out.println("Navigating to Add Events...");
        SceneController.changeScene(event, "addEventForm.fxml", "Add Event", Session.getUsername(), Session.getRole());
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        if (selectedId == -1) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an event to update.");
            return;
        }

        String name = eventNameTextField.getText();
        String location = locationTextField.getText();
        LocalDate date = eventDatePicker.getValue();
        String image = imagePathTextField.getText();
        String desc = descriptionTextField.getText();

        if (name.isEmpty() || location.isEmpty() || date == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please fill all required fields.");
            return;
        }

        String query = "UPDATE events SET event_name = ?, location = ?, event_date = ?, image_path = ?, description = ? WHERE event_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, location);
            ps.setDate(3, Date.valueOf(date));
            ps.setString(4, image);
            ps.setString(5, desc);
            ps.setInt(6, selectedId);
            ps.executeUpdate();


            showAlert(Alert.AlertType.INFORMATION, "Success", "Event updated successfully!");
            clearFields();
            loadTableData();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Update Error", e.getMessage());
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        if (selectedId == -1) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an event to delete.");
            return;
        }

        String query = "DELETE FROM events WHERE event_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, selectedId);
            ps.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Event deleted successfully!");
            clearFields();
            loadTableData();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Delete Error", e.getMessage());
        }
    }

    private void clearFields() {
        eventNameTextField.clear();
        locationTextField.clear();
        eventDatePicker.setValue(null);
        imagePathTextField.clear();
        descriptionTextField.clear();
        selectedId = -1;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
