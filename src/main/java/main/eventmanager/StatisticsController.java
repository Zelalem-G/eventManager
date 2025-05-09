package main.eventmanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatisticsController {

    @FXML
    private Label totalUsersLabel;
    @FXML
    private Label totalEventsLabel;
    @FXML
    private Label totalRegistrationsLabel;
    @FXML
    private Label averageFeedbackLabel;

    public void initialize() {
        // Here you can query the database or data source to get the actual statistics

        // Sample data for now
        totalUsersLabel.setText("123");
        totalEventsLabel.setText("45");
        totalRegistrationsLabel.setText("350");
        averageFeedbackLabel.setText("4.5");
    }
}

