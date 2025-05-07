package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button browseEventCardBtn;

    @FXML
    private Button myRegisteredCardBtn;

    @FXML
    private Button accSettingCardBtn;

    @FXML
    private Button aboutUsCardBtn;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem adminEventBtn;

    @FXML
    private MenuItem adminOpsBtn;

//    @FXML
//    private MenuItem browseEventsBtn;
//
//    @FXML
//    private MenuItem myRegisteredEventsBtn;
//
//    @FXML
//    private MenuItem aboutUsBtn;
//
//    @FXML
//    private VBox mainVBox; // fx:id of your VBox in FXML
//
//    @FXML
//    private StackPane stackPane;


    private MenuControllerHelper menuHelper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuHelper = new MenuControllerHelper(welcomeLabel, adminEventBtn, adminOpsBtn);
        menuHelper.initializeMenu(Session.getUsername(), Session.getRole());
        // Call setUserInformation here when the controller is initialized
        //setUserInformation(Session.getUsername(), Session.getRole());
        styleCardButtons();
    }


    // Method for browsing events
    public void onBrowseEvents(ActionEvent event) {
        System.out.println("Browsing events...");
        SceneController.changeScene(event, "events.fxml", "Available Events", Session.getUsername(), Session.getRole());
    }

    // Method for viewing registered events
    public void onMyRegisteredEvents(ActionEvent event) {
        System.out.println("Viewing registered events...");
        SceneController.changeScene(event, "myRegisteredEvents.fxml", "My Registered Events", Session.getUsername(), Session.getRole());
    }

    // Method for about us section
    public void onAbout(ActionEvent event) {
        System.out.println("Navigating to About Us...");
        SceneController.changeScene(event, "aboutUs.fxml", "About Us", Session.getUsername(), Session.getRole());
    }

    // Method for account settings
    public void onAccount(ActionEvent event) {
        System.out.println("Navigating to Account Settings...");
        SceneController.changeScene(event, "accountSettings.fxml", "Account Settings", Session.getUsername(), Session.getRole());
    }

    // To handle the menu clicks dynamically
    @FXML
    private void onMenuClick(ActionEvent event) {
        if (menuHelper != null) {
            menuHelper.handleMenuClick(event);
        }
    }

    public void styleCardButtons() {
        aboutUsCardBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea, #764ba2);");
        myRegisteredCardBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #f7971e, #ffd200);");
        browseEventCardBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #00c6ff, #0072ff);");
        accSettingCardBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #43cea2, #185a9d);");
    }


}