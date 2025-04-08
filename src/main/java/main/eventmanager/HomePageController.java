package main.eventmanager;

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
//    @FXML
//    private Label welcomUser;
//    public void setUserInformation(String userName){
//        welcomUser.setText("Hello, "+ userName);
//    }

    @FXML
    private Label welcomeLabel;

    public void setUserInformation(String username, String role) {
        welcomeLabel.setText("Welcome " + username + "! Role: " + role);
    }
}
