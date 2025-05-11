package main.eventmanager;

import EventModel.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisteredEventCardController {

    @FXML private ImageView eventImage;
    @FXML private Label eventName;
    @FXML private Label eventLocation;
    @FXML private Label eventDate;
    @FXML private Text eventDescription;
    @FXML private Button cancelButton;

    private Event event;
    private Runnable onCancel;

    public void setEvent(Event event, Runnable onCancel) {
        this.event = event;
        this.onCancel = onCancel;

        eventName.setText(event.getEventName());
        eventLocation.setText("📍 " + event.getEventLocation());
        eventDate.setText("📅 " + event.getEventDate());
        eventDescription.setText(event.getDescription());

        try {
//            System.out.println(event.getEventImage());
            Image image = new Image(getClass().getResource(event.getEventImage()).toExternalForm());
            eventImage.setImage(image);
        } catch (Exception e) {
            System.out.println("Image load failed: " + e.getMessage());
        }
    }

    @FXML
    private void cancelRegistration() {
        String deleteRegistration = "DELETE FROM registrations WHERE user_id = ? AND event_id = ?";
        String deleteAttendee = "DELETE FROM attendees WHERE user_id = ?";

        try (Connection conn = DBUtils.getConnection()) {
            conn.setAutoCommit(false); // Transaction start

            try (
                    PreparedStatement ps1 = conn.prepareStatement(deleteRegistration);
                    PreparedStatement ps2 = conn.prepareStatement(deleteAttendee)
            ) {
                ps1.setInt(1, Session.getUserId());
                ps1.setInt(2, event.getEventId());
                ps1.executeUpdate();

                ps2.setInt(1, Session.getUserId());
                ps2.executeUpdate();

                conn.commit(); // Commit if both succeed
                System.out.println("Registration and attendee info deleted.");

                if (onCancel != null) onCancel.run(); // Refresh UI

            } catch (SQLException e) {
                conn.rollback(); // Rollback on error
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
