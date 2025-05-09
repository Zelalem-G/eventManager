package main.eventmanager;

public class AdminDashboardController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private MenuItem adminEventBtn, adminOpsBtn;
    private MenuControllerHelper menuHelper;

    @FXML private StackPane contentArea;

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
        loadView("/views/dashboard/UsersView.fxml");
    }

    @FXML
    private void handleFeedbackView(ActionEvent event) {
        loadView("/views/dashboard/FeedbackView.fxml");
    }

    @FXML
    private void handleAttendeesView(ActionEvent event) {
        loadView("/views/dashboard/AttendeesView.fxml");
    }

    @FXML
    private void handleStatsView(ActionEvent event) {
        loadView("/views/dashboard/StatsView.fxml");
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

