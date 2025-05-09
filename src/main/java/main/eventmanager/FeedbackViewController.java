package main.eventmanager;

import database.DatabaseConnection;
import helpers.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.FeedbackData;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class FeedbackViewController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<FeedbackData> feedbackTable;

    @FXML
    private TableColumn<FeedbackData, String> usernameCol;

    @FXML
    private TableColumn<FeedbackData, Integer> ratingCol;

    @FXML
    private TableColumn<FeedbackData, String> messageCol;

    @FXML
    private TableColumn<FeedbackData, String> createdAtCol;

    private ObservableList<FeedbackData> feedbackList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("Hello, " + Session.getUsername());

        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        loadFeedback();
    }

    private void loadFeedback() {
        String query = "SELECT f.rating, f.message, f.created_at, u.username FROM feedback f JOIN users u ON f.user_id = u.user_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                feedbackList.add(new FeedbackData(
                        rs.getString("username"),
                        rs.getInt("rating"),
                        rs.getString("message"),
                        rs.getString("created_at")
                ));
            }
            feedbackTable.setItems(feedbackList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

