package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuButton;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem adminEventBtn;

    @FXML
    private MenuItem adminOpsBtn;

    @FXML
    private MenuItem browseEventsBtn;

    @FXML
    private MenuItem myRegisteredEventsBtn;

    @FXML
    private MenuItem aboutUsBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Call setUserInformation here when the controller is initialized
        setUserInformation(Session.getUsername(), Session.getRole());
    }

    // Method to set user information dynamically based on role
    public void setUserInformation(String username, String role) {
        welcomeLabel.setText("Welcome back, " + username + "!");

        // Check if the user is an admin and toggle menu options accordingly
        if ("admin".equalsIgnoreCase(role)) {
            adminEventBtn.setVisible(true);  // Show admin options
            adminOpsBtn.setVisible(true);    // Show admin operations
        } else {
            adminEventBtn.setVisible(false);  // Hide admin options for non-admin
            adminOpsBtn.setVisible(false);    // Hide admin operations for non-admin
        }
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


    // Admin method to manage events (add, update, delete)
    public void onManageEvents(ActionEvent event) {
        System.out.println("Admin managing events...");
        SceneController.changeScene(event, "eventCRUD.fxml", "Manage Events", Session.getUsername(), Session.getRole());
    }

    // Admin operation method for additional admin features
    public void onAdminOps(ActionEvent event) {
        System.out.println("Admin accessing operations...");
        SceneController.changeScene(event, "adminOperations.fxml", "Admin Operations", Session.getUsername(), Session.getRole());
    }

    // To handle the menu clicks dynamically
    @FXML
    public void onMenuClick(ActionEvent event) {
        MenuItem clickedMenuItem = (MenuItem) event.getSource();
        String menuOption = clickedMenuItem.getText();  // Get the text of the clicked MenuItem

        System.out.println("Menu Option Clicked: " + menuOption); // Debugging line

        switch (menuOption) {
            case "Home":
                SceneController.changeScene(event, "homePage.fxml", "Home", Session.getUsername(), Session.getRole());
                break;
            case "Available Events":
                onBrowseEvents(event);
                break;
            case "Registered Events":
                onMyRegisteredEvents(event);
                break;
            case "Account":
                onAccount(event);
                break;
            case "About Us":
                onAbout(event);
                break;
            case "Manage Events (Admin)":
                onManageEvents(event);
                break;
            case "Admin Operations":
                onAdminOps(event);
                break;
            default:
                break;
        }
    }

}
