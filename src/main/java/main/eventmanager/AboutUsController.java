package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AboutUsController {

    @FXML private TextArea feedbackTextArea;
    @FXML private HBox starBox;

    private int selectedRating = 0;
    private final Label[] stars = new Label[5];
    private Connection connection;

    @FXML
    public void initialize() {
        try {
            connection = DBUtils.getConnection();
            if (connection == null) {
                throw new SQLException("Connection is null. Check your database setup.");
            }
            createStarRating();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Initialization Error", "Failed to initialize About Us page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createStarRating() {
        for (int i = 0; i < 5; i++) {
            Label star = new Label("☆");
            final int starIndex = i + 1;
            star.getStyleClass().add("star");
            star.setOnMouseClicked(event -> setRating(starIndex));
            stars[i] = star;
            starBox.getChildren().add(star);
        }
    }

    private void setRating(int rating) {
        selectedRating = rating;
        for (int i = 0; i < 5; i++) {
            stars[i].setText(i < rating ? "★" : "☆");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        SceneController.changeScene(event, "home.fxml", "Home", Session.getUsername(), Session.getRole());
    }

    @FXML
    private void handleSubmitFeedback() {
        String comment = feedbackTextArea.getText().trim();
        int userId = Session.getUserId();

        if (comment.isEmpty() || selectedRating == 0) {
            showAlert(Alert.AlertType.WARNING, "Incomplete", "Please provide both feedback and a rating.");
            return;
        }

        String query = "INSERT INTO feedback (user_id, message, rating) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setString(2, comment);
            ps.setInt(3, selectedRating);
            ps.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Thank you for your feedback!");
            feedbackTextArea.clear();
            setRating(0);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to submit feedback: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
