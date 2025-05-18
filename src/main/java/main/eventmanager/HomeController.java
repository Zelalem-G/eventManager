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
    private Button manageEventCardBtn;

    @FXML
    private Button operationsCardBtn;

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

    @FXML
    private MenuItem browseEventsMenuItem;

    @FXML
    private MenuItem myRegisteredMenuItem;

    @FXML
    private MenuItem aboutMenuItem;
//
//    @FXML
//    private VBox mainVBox; // fx:id of your VBox in FXML
//
//    @FXML
//    private StackPane stackPane;


    private MenuControllerHelper menuHelper;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuHelper = new MenuControllerHelper(welcomeLabel, adminEventBtn, adminOpsBtn, aboutMenuItem, browseEventsMenuItem, myRegisteredMenuItem);
        menuHelper.initializeMenu(Session.getUsername(), Session.getRole());

        if("admin".equalsIgnoreCase(Session.getRole())){
            aboutUsCardBtn.setVisible(false);
            aboutUsCardBtn.setManaged(false);

            browseEventCardBtn.setVisible(false);
            browseEventCardBtn.setManaged(false);

            myRegisteredCardBtn.setVisible(false);
            myRegisteredCardBtn.setManaged(false);
        } else{
            operationsCardBtn.setVisible(false);
            operationsCardBtn.setManaged(false);

            manageEventCardBtn.setVisible(false);
            manageEventCardBtn.setManaged(false);
        }
//        styleCardButtons();

    }

    //  Methods for Admin
    public void onManageEvents(ActionEvent event) {
        System.out.println("Viewing manage events...");
        SceneController.changeScene(event, "eventCRUD.fxml", "Manage Events", Session.getUsername(), Session.getRole());
    }

    public void onOperationsEvents(ActionEvent event) {
        System.out.println("Viewing admin dashboard events...");
        SceneController.changeScene(event, "AdminDashboard.fxml", "Admin operations", Session.getUsername(), Session.getRole());
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
        SceneController.changeScene(event, "AboutUs.fxml", "About Us", Session.getUsername(), Session.getRole());
    }

    // Method for account settings
    public void onAccount(ActionEvent event) {
        System.out.println("Navigating to Account Settings...");
        SceneController.changeScene(event, "myAccount.fxml", "Account", Session.getUsername(), Session.getRole());
    }

    // To handle the menu clicks dynamically
    @FXML
    private void onMenuClick(ActionEvent event) {
        if (menuHelper != null) {
            menuHelper.handleMenuClick(event);
        }
    }

}