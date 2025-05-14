package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class MenuControllerHelper {

    private final Label welcomeLabel;
    private final MenuItem adminEventBtn;
    private final MenuItem adminOpsBtn;
    private final MenuItem aboutMenuItem;
    private final MenuItem browseEventsMenuItem;
    private final MenuItem myRegisteredMenuItem;

    public MenuControllerHelper(Label welcomeLabel, MenuItem adminEventBtn, MenuItem adminOpsBtn, MenuItem aboutUsBtn, MenuItem browseEventsMenuItem, MenuItem myRegisteredMenuItem) {
        this.welcomeLabel = welcomeLabel;
        this.adminEventBtn = adminEventBtn;
        this.adminOpsBtn = adminOpsBtn;
        this.aboutMenuItem = aboutUsBtn;
        this.browseEventsMenuItem = browseEventsMenuItem;
        this.myRegisteredMenuItem = myRegisteredMenuItem;
    }

    public void initializeMenu(String username, String role) {
        welcomeLabel.setText("Welcome back, " + username + "!");

        boolean isAdmin = "admin".equalsIgnoreCase(role);
        adminEventBtn.setVisible(isAdmin);
        adminOpsBtn.setVisible(isAdmin);
        aboutMenuItem.setVisible(!isAdmin);  // visible if it is not admin
        browseEventsMenuItem.setVisible(!isAdmin);
        myRegisteredMenuItem.setVisible(!isAdmin);
    }

    public void handleMenuClick(ActionEvent event) {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String text = clickedItem.getText();

        switch (text) {
            case "Home":
                SceneController.changeScene(event, "home.fxml", "Home", Session.getUsername(), Session.getRole());
                break;
            case "Available Events":
                SceneController.changeScene(event, "events.fxml", "Available Events", Session.getUsername(), Session.getRole());
                break;
            case "Registered Events":
                SceneController.changeScene(event, "myRegisteredEvents.fxml", "My Registered Events", Session.getUsername(), Session.getRole());
                break;
            case "Account":
                SceneController.changeScene(event, "myAccount.fxml", "Account Settings", Session.getUsername(), Session.getRole());
                break;
            case "About Us":
                SceneController.changeScene(event, "aboutUs.fxml", "About Us", Session.getUsername(), Session.getRole());
                break;
            case "Manage Events (Admin)":
                System.out.println("Admin managing events...");
                SceneController.changeScene(event, "eventCRUD.fxml", "Manage Events", Session.getUsername(), Session.getRole());
                break;
            case "Admin Operations":
                System.out.println("Admin accessing operations...");
                SceneController.changeScene(event, "AdminDashboard.fxml", "Admin Operations", Session.getUsername(), Session.getRole());
                break;
        }
    }
}
