package main.eventmanager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomePageController {

    @FXML
    private Label welcomeLabel;

    public void setUserInformation(String username, String role) {
        welcomeLabel.setText("Welcome back,  " + username + "!");
    }
    public void onRegisterEvents(ActionEvent event){
//        System.out.println("Setting user info for: ................................................." );
        SceneController.changeScene(event,"addEventForm.fxml","Add event", Session.getUsername(),Session.getRole());
    }

    public void onBrowseEvents(ActionEvent event){
        System.out.println("ON BROWSE EVENT BUTTON CLICKED");
        SceneController.changeScene(event,"events.fxml","events", Session.getUsername(),Session.getRole());
    }


}
