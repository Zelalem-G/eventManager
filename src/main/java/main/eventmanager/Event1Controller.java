package main.eventmanager;

import EventModel.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;

public class Event1Controller {

    @FXML private ScrollPane descriptionScrollPane;
    @FXML private Text description;
    @FXML private Label eventDate;
    @FXML private ImageView eventImage;
    @FXML private Label eventLocation;
    @FXML private Label eventName;

    private Event currentEvent; //  Store the event instance

    // Set event data into UI
    public void setData(Event event) {
        this.currentEvent = event; //  Save event for use in register handler

        // Load and set the image
        String imagePath = event.getEventImage();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image img = new Image(imageStream);
            eventImage.setImage(img);
        } else {
            System.out.println("Image not found: " + imagePath);
        }

        // Set other fields
        eventName.setText(event.getEventName());
        eventDate.setText(event.getEventDate());
        eventLocation.setText(event.getEventLocation());
        description.setText(event.getDescription());
    }

    // Handle register button
    @FXML
    void handleRegistration(ActionEvent event) throws IOException {
        System.out.println("ON REGISTER FOR AN EVENT BUTTON CLICKED");

        //  Set the event ID in session
        if (currentEvent != null) {
            Session.setSessionEventId(currentEvent.getEventId());
            System.out.println("Registered for event ID: " + currentEvent.getEventId());
        }

        //  Navigate to registration scene
        SceneController.changeScene(event, "register.fxml", "Register", Session.getUsername(), Session.getRole());
    }

    // Toggle expand/collapse for description
    @FXML
    private void expandDescription() {
        double currentHeight = descriptionScrollPane.getPrefHeight();
        if (currentHeight == 36.0) {
            descriptionScrollPane.setPrefHeight(100.0);
        } else {
            descriptionScrollPane.setPrefHeight(36.0);
        }
    }
}
