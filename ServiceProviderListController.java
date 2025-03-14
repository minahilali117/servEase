package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ServiceProviderListController {

    @FXML private TableView<ServiceProvider> serviceProviderTable;
    @FXML private TableColumn<ServiceProvider, String> nameColumn;
    @FXML private TableColumn<ServiceProvider, String> serviceColumn;
    @FXML private TableColumn<ServiceProvider, String> emailColumn;
    @FXML private TableColumn<ServiceProvider, String> phoneColumn;
    @FXML private TableColumn<ServiceProvider, Integer> ratingColumn;

    @FXML private Button deleteServiceProviderButton; // Delete Service Provider Button

    private ServiceProviderDBHandler serviceProviderDBHandler = new ServiceProviderDBHandler();

    @FXML
    public void initialize() {
        // Initialize the columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        // Fetch service providers from the database and populate the table
        loadServiceProviderData();

        // Disable delete button if no service provider is selected
        deleteServiceProviderButton.setDisable(true);

        // Add listener for service provider selection
        serviceProviderTable.setOnMouseClicked((MouseEvent event) -> {
            if (serviceProviderTable.getSelectionModel().getSelectedItem() != null) {
                deleteServiceProviderButton.setDisable(false);  // Enable delete button when a service provider is selected
            }
        });
    }

    private void loadServiceProviderData() {
        List<ServiceProvider> serviceProviders = serviceProviderDBHandler.getAllServiceProviders();
        ObservableList<ServiceProvider> serviceProviderList = FXCollections.observableArrayList(serviceProviders);
        serviceProviderTable.setItems(serviceProviderList);
    }

    @FXML
    public void handleDeleteServiceProvider() {
        ServiceProvider selectedServiceProvider = serviceProviderTable.getSelectionModel().getSelectedItem();
        if (selectedServiceProvider != null) {
            serviceProviderDBHandler.deleteServiceProvider(selectedServiceProvider.getProviderId());
            loadServiceProviderData(); // Refresh the table after deleting the service provider
            showAlert("Service Provider Deleted", "The selected service provider has been deleted successfully.");
        }
    }

    @FXML private Button backButton;

    // Method to handle the Back button click event
    @FXML
    public void handleBackButton() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Print the full stack trace to see the error
            showAlert("Error", "Unable to load Admin Dashboard. Please check the FXML path.");
        }
    }

    private Button readFeedbackButton;

    // Called when the "Read Feedback" button is clicked
    @FXML
    public void readFeedback() {
        ServiceProvider selectedServiceProvider = serviceProviderTable.getSelectionModel().getSelectedItem();
        
        if (selectedServiceProvider == null) {
            showAlert("No Service Provider Selected", "Please select a service provider to read their feedback.");
            return;
        }

        FeedbackDBHandler feedbackDBHandler = new FeedbackDBHandler();
        List<Feedback> feedbacks = feedbackDBHandler.getAllFeedbacks(); // Retrieve all feedbacks
        StringBuilder serviceProviderFeedback = new StringBuilder();

        // Loop through feedbacks and find feedback for the selected service provider
        for (Feedback feedback : feedbacks) {
            if (feedback.getBookingID() == selectedServiceProvider.getProviderId()) {
                serviceProviderFeedback.append("Booking ID: ").append(feedback.getBookingID())
                        .append("\nFeedback: ").append(feedback.getFeedback())
                        .append("\nRating: ").append(feedback.getRating()).append("/5\n\n");
            }
        }

        if (serviceProviderFeedback.length() == 0) {
            showAlert("No Feedback Found", "This service provider has no feedback.");
        } else {
            showFeedbackPopup(serviceProviderFeedback.toString());
        }
    }
    
    // Helper method to show feedback in a popup
    private void showFeedbackPopup(String feedbackText) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Service Provider Feedback");
        alert.setHeaderText("Feedback for Selected Service Provider:");
        alert.setContentText(feedbackText);
        alert.showAndWait();
    }

    // Show alert for successful actions
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}