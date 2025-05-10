package main.eventmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UsersViewController implements Initializable {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> fullNameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> roleCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadUsers();
    }

    private void loadUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users ORDER BY user_id")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("loop bigins");
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("role")
                );
                System.out.println("b");
                users.add(user);
                System.out.println("a");
            }
        } catch (SQLException e) {
            System.out.println("Exception");
            e.printStackTrace();
        }

        userTable.setItems(users);
    }
}

