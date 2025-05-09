package main.eventmanager;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class UserController {

    @FXML private TableColumn<User, String> nameCol;
    @FXML private TableColumn<User, Integer> ageCol;
    @FXML private TableColumn<User, String> emailCol;
    @FXML private TableColumn<User, String> phoneCol;
    @FXML private TableView<User> userTableView;
    @FXML private Label eventName;
    @FXML private Button searchButton;
    @FXML private TextField searchTextField;

    private ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    public void handleSearchButton(MouseEvent event) {

        String searchQuery = searchTextField.getText().trim();
        if (searchQuery.isEmpty()) {
            eventName.setText("Please enter an event name.");
            return;
        }
        users.clear();

        try (Connection conn = DBUtils.getConnection()) {
            String sql = """
                    SELECT u.name, u.age,u.phone , u.email
                    FROM users u 
                    JOIN events e ON u.event_id = e.id 
                    WHERE e.event_name = ?
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, searchQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }

            userTableView.setItems(users);
            eventName.setText(searchQuery);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        ageCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAge()).asObject());
        phoneCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));
        emailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        userTableView.setItems(users);
    }
}
