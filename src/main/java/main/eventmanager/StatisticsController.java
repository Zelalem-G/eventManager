package main.eventmanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsController {

    @FXML
    private Label totalUsersLabel;

    @FXML
    private Label totalEventsLabel;

    @FXML
    private Label totalRegistrationsLabel;

    @FXML
    private Label averageFeedbackLabel;

    @FXML
    public void initialize() {
        totalUsersLabel.setText(String.valueOf(getTotalUsers()));
        totalEventsLabel.setText(String.valueOf(getTotalEvents()));
        totalRegistrationsLabel.setText(String.valueOf(getTotalRegistrations()));
        averageFeedbackLabel.setText(String.format("%.2f", getAverageFeedbackRating()));
    }

    private int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getTotalEvents() {
        String sql = "SELECT COUNT(*) FROM events";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getTotalRegistrations() {
        String sql = "SELECT COUNT(*) FROM registrations";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private double getAverageFeedbackRating() {
        String sql = "SELECT AVG(rating) FROM feedback";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
