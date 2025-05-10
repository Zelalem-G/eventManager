package main.eventmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.FeedbackData;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class FeedbackViewController implements Initializable {

    @FXML
    private TableView<FeedbackData> feedbackTable;

    @FXML
    private TableColumn<FeedbackData, String> usernameCol;

    @FXML
    private TableColumn<FeedbackData, Integer> ratingCol;

    @FXML
    private TableColumn<FeedbackData, String> messageCol;

    @FXML
    private TableColumn<FeedbackData, String> dateCol;

    private final ObservableList<FeedbackData> feedbackList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        loadFeedback();
    }

    private void loadFeedback() {
        String query = """
                SELECT u.username, f.rating, f.message, f.created_at
                FROM feedback f
                JOIN users u ON f.user_id = u.user_id
                ORDER BY f.created_at DESC;
                """;

        try (Connection conn = DBUtils.getConnection();
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
