package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import EventModel.Event;
import javafx.scene.layout.VBox;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Node;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private GridPane myGrid;

    private List<Event> events;

    @FXML
    private Label welcomeLabel;

    @FXML
    private MenuItem adminEventBtn;

    @FXML
    private MenuItem adminOpsBtn;

    private MenuControllerHelper menuHelper;

    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private VBox eventListVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuHelper = new MenuControllerHelper(welcomeLabel, adminEventBtn, adminOpsBtn);
        menuHelper.initializeMenu(Session.getUsername(), Session.getRole());

        events = new ArrayList<>(fetchEventsFromDatabase());
        int col = 0;
        int row = 1;

        try {
            for (Event event : events) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("event1.fxml"));
                pane = fxmlLoader.load();

                Event1Controller event1Controller = fxmlLoader.getController();
                event1Controller.setData(event);  // Pass full event object, including eventId

                if (col == 3) {
                    col = 0;
                    row++;
                }

                myGrid.add(pane, col++, row);
                GridPane.setMargin(pane, new Insets(10));
            }
        } catch (IOException e) {
            System.out.println("ERROR IN MAINCONTROLLER initialize TRY CATCH");
            e.printStackTrace();
        }
        searchButton.setOnAction(e -> onSearchClicked());
        searchField.setOnAction(event -> onSearchClicked());

    }

    @FXML
    private void onMenuClick(ActionEvent event) {
        if (menuHelper != null) {
            menuHelper.handleMenuClick(event);
        }
    }


    private List<Event> fetchEventsFromDatabase() {
        List<Event> eventList = new ArrayList<>();
        String query = "SELECT event_id, image_path, event_name, event_date, location, description FROM events";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int eventId = rs.getInt("event_id");
                String imagePath = rs.getString("image_path");
                String eventName = rs.getString("event_name");
                String eventDate = rs.getDate("event_date").toString(); // Convert DATE to String
                String eventLocation = rs.getString("location");
                String description = rs.getString("description");

                Event event = createEvent(eventId, imagePath, eventName, eventDate, eventLocation, description);
                eventList.add(event);
            }

        } catch (SQLException e) {
            System.out.println("ERROR IN MAINCONTROLLER: Fetch events from database SQL exception TRY CATCH");
            e.printStackTrace();
        }

        return eventList;
    }

    private Event createEvent(int eventId, String imagePath, String eventName, String eventDate, String eventLocation, String description) {
        Event event = new Event();
        event.setEventId(eventId);
        event.setEventImage(imagePath);
        event.setEventName(eventName);
        event.setEventDate(eventDate);
        event.setEventLocation(eventLocation);
        event.setDescription(description);
        return event;
    }

    @FXML
    private void onSearchClicked() {
        String query = searchField.getText().toLowerCase().trim();
        myGrid.getChildren().clear();

        // Normalize date string (e.g., from 09/05/2025 to 2025-05-09)
        String normalizedDate = normalizeDate(query);

        int col = 0;
        int row = 1;

        try {
            for (Event event : events) {
                boolean matchesName = event.getEventName().toLowerCase().contains(query);
                boolean matchesLocation = event.getEventLocation().toLowerCase().contains(query);
                boolean matchesDate = false;
                if (normalizedDate != null) {
                    matchesDate = event.getEventDate().equals(normalizedDate);
                }

                // If query is empty, reload all events
                if (query.isEmpty() || matchesName || matchesLocation || matchesDate) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("event1.fxml"));
                    Pane pane = fxmlLoader.load();

                    Event1Controller controller = fxmlLoader.getController();
                    controller.setData(event);

                    if (col == 3) {
                        col = 0;
                        row++;
                    }

                    myGrid.add(pane, col++, row);
                    GridPane.setMargin(pane, new Insets(10));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String normalizeDate(String input) {
        String[] formats = {"dd/MM/yyyy", "MM/dd/yyyy", "dd-MM-yyyy", "yyyy-MM-dd"};

        for (String format : formats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDate date = LocalDate.parse(input, formatter);
                return date.toString();  // Always return in yyyy-MM-dd format
            } catch (DateTimeParseException ignored) {}
        }

        return null;  // Not a valid date input
    }


}
