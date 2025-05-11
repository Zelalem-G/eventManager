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

    @FXML private ImageView eventImage;
    @FXML private Label eventName;
    @FXML private Label eventDate;
    @FXML private Label eventLocation;
    @FXML private Text description;
    @FXML private ScrollPane descriptionScrollPane;

    private Event currentEvent;

    public void setData(Event event) {
        this.currentEvent = event;

        // Load and set the image
        String imagePath = event.getEventImage();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image img = new Image(imageStream);
            eventImage.setImage(img);
        } else {
            System.out.println("Image not found: " + imagePath);
        }

        eventName.setText(event.getEventName());
        eventDate.setText(event.getEventDate());
        eventLocation.setText(event.getEventLocation());
        description.setText(event.getDescription());
    }

    @FXML
    void handleRegistration(ActionEvent event) throws IOException {
        System.out.println("ON REGISTER FOR AN EVENT BUTTON CLICKED");

        if (currentEvent != null) {
            Session.setSessionEventId(currentEvent.getEventId());
            System.out.println("Registered for event ID: " + currentEvent.getEventId());
        }

        SceneController.changeScene(event, "register.fxml", "Register", Session.getUsername(), Session.getRole());
    }

    @FXML
    private void expandDescription() {
        double currentHeight = descriptionScrollPane.getPrefHeight();
        if (currentHeight == 60.0) {
            descriptionScrollPane.setPrefHeight(120.0);
        } else {
            descriptionScrollPane.setPrefHeight(60.0);
        }
    }
}
