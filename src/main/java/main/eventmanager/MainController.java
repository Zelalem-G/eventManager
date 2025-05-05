package main.eventmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import EventModel.Event;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private GridPane myGrid;

    private List<Event> events;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
}
