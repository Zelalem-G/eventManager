package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {
    @FXML
    private Label welcomeLabel;

    @FXML
    private MenuItem adminEventBtn;

    @FXML
    private MenuItem adminOpsBtn;

    private MenuControllerHelper menuHelper;

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuHelper = new MenuControllerHelper(welcomeLabel, adminEventBtn, adminOpsBtn);
        menuHelper.initializeMenu(Session.getUsername(), Session.getRole());
    }

    @FXML
    private void onMenuClick(ActionEvent event) {
        if (menuHelper != null) {
            menuHelper.handleMenuClick(event);
        }
    }

    @FXML
    private void handleUserView(ActionEvent event) {
        loadView("UsersView.fxml");
    }

    @FXML
    private void handleFeedbackView(ActionEvent event) {
        loadView("feedback_view.fxml");
    }

    @FXML
    private void handleAttendeesView(ActionEvent event) {
        loadView("attendees_view.fxml");
    }

    @FXML
    private void handleStatsView(ActionEvent event) {
        loadView("statistics.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

