package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignupController {
    private Scene scene;
    private Parent root;
    private Stage stage;
    @FXML
    private Label errorMessageLabel;

    @FXML
    private TextField passwordTextfield;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField emailTextfield;
    @FXML
    private TextField fullNameTextfield;
    @FXML
    private RadioButton userRadioBtn;
    @FXML
    private RadioButton adminRadioBtn;
    @FXML
    private TextField adminCodeTextField;


    public void onRoleSelected() {
        boolean isAdmin = adminRadioBtn.isSelected();
        adminCodeTextField.setVisible(isAdmin);
    }

    public void onSignup(ActionEvent event) throws IOException {
        String username = usernameTextfield.getText().trim();
        String password = passwordTextfield.getText().trim();
        String email = emailTextfield.getText().trim();
        String fullName = fullNameTextfield.getText().trim();
        String role = selectedRole();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || fullName.isEmpty() || role == null) {
            errorMessageLabel.setText("Please fill in all fields.");
            return;
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            errorMessageLabel.setText("Invalid email address format!");
            return;
        }
        if(password.length() < 8 || password.length() > 16){
            errorMessageLabel.setText("Password must be between 8 and 16 character");
            return;
        }

        if (role.equals("admin")) {
            String enteredCode = adminCodeTextField.getText().trim();
            final String ADMIN_SECRET = "ABC123";

            if (!enteredCode.equals(ADMIN_SECRET)) {
                errorMessageLabel.setText("Invalid admin verification code.");
                return;
            }
        }

        AuthService.SignupResult result = AuthService.registerUser(username, password, email , fullName, role);

        switch (result) {
            case SUCCESS:
                Session.setSession(username , role);
                AuthService.setUserIDToSession(username);
                SceneController.changeScene(event, "home.fxml", "Home", username, role);
                return;

            case USERNAME_TAKEN:
                errorMessageLabel.setText("Username already taken. Try another.");
                break;

            case ERROR:
                errorMessageLabel.setText("Something went wrong. Please try again later.");
                break;
        }

    }

    public void onLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public String selectedRole() {
        String selectedRole = "";
        if (adminRadioBtn.isSelected()) {
            selectedRole = "admin";
        } else if (userRadioBtn.isSelected()) {
            selectedRole = "user";
        }
        return selectedRole;
    }
}