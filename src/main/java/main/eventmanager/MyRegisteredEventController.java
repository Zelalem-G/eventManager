package main.eventmanager;

import EventModel.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyRegisteredEventController implements Initializable {
    @FXML private FlowPane eventFlowPane;
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
        loadRegisteredEvents();
    }

    @FXML
    private void onMenuClick(ActionEvent event) {
        if (menuHelper != null) {
            menuHelper.handleMenuClick(event);
        }
    }

    private void loadRegisteredEvents() {
        eventFlowPane.getChildren().clear();
        List<Event> events = getRegisteredEventsFromDB(Session.getUserId());

        for (Event event : events) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("registered_event_card.fxml"));
                Node card = loader.load();

                RegisteredEventCardController cardController = loader.getController();
                cardController.setEvent(event, this::loadRegisteredEvents); // Reload after cancel

                eventFlowPane.getChildren().add(card);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<Event> getRegisteredEventsFromDB(int userId) {
        List<Event> list = new ArrayList<>();
        String query = """
            SELECT e.event_id, e.event_name, e.description, e.location, e.event_date, e.image_path
            FROM events e
            JOIN registrations r ON e.event_id = r.event_id
            WHERE r.user_id = ?
        """;

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Event e = new Event();
                e.setEventId(rs.getInt("event_id"));
                e.setEventName(rs.getString("event_name"));
                e.setDescription(rs.getString("description"));
                e.setEventLocation(rs.getString("location"));
                e.setEventDate(rs.getString("event_date"));
                e.setEventImage(rs.getString("image_path"));
                list.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
