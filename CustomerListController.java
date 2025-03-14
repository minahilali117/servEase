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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CustomerListController {

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> emailColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private TableColumn<Customer, String> genderColumn;
    @FXML private TableColumn<Customer, String> cityColumn;
    @FXML private TableColumn<Customer, Integer> ratingColumn;
    @FXML private TableColumn<Customer, Integer> numOfServicesColumn;

    @FXML private Button addCustomerButton; // Add Customer Button
    @FXML private Button deleteCustomerButton; // Delete Customer Button

    private CustomerDBHandler customerDBHandler = new CustomerDBHandler();

    @FXML
    public void initialize() {
        // Initialize the columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        numOfServicesColumn.setCellValueFactory(new PropertyValueFactory<>("numOfServicesTaken"));

        // Fetch customers from the database and populate the table
        loadCustomerData();

        // Disable delete button if no customer is selected
        deleteCustomerButton.setDisable(true);

        // Add listener for customer selection
        customerTable.setOnMouseClicked((MouseEvent event) -> {
            if (customerTable.getSelectionModel().getSelectedItem() != null) {
                deleteCustomerButton.setDisable(false);  // Enable delete button when a customer is selected
            }
        });
    }

    private void loadCustomerData() {
        List<Customer> customers = customerDBHandler.getAllCustomers();
        ObservableList<Customer> customerList = FXCollections.observableArrayList(customers);
        customerTable.setItems(customerList);
    }


    @FXML
    public void handleDeleteCustomer() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            customerDBHandler.deleteCustomer(selectedCustomer.getCustomerId());
            loadCustomerData(); // Refresh the table after deleting the customer
            showAlert("Customer Deleted", "The selected customer has been deleted successfully.");
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

    

    private Button ReadFeedback;

    // Called when the "Read Feedback" button is clicked
    @FXML
    public void readFeedback() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        
        if (selectedCustomer == null) {
            showAlert("No Customer Selected", "Please select a customer to read their feedback.");
            return;
        }

        FeedbackDBHandler feedbackDBHandler = new FeedbackDBHandler();
        List<Feedback> feedbacks = feedbackDBHandler.getAllFeedbacks(); // Retrieve all feedbacks
        StringBuilder customerFeedback = new StringBuilder();

        // Loop through feedbacks and find feedback for the selected customer
        for (Feedback feedback : feedbacks) {
            if (feedback.getCustomerId() == selectedCustomer.getCustomerId()) {
                customerFeedback.append("Booking ID: ").append(feedback.getBookingID())
                        .append("\nFeedback: ").append(feedback.getFeedback())
                        .append("\nRating: ").append(feedback.getRating()).append("/5\n\n");
            }
        }

        if (customerFeedback.length() == 0) {
            showAlert("No Feedback Found", "This customer has no feedback.");
        } else {
            showFeedbackPopup(customerFeedback.toString());
        }
    }
    
    // Helper method to show feedback in a popup
    private void showFeedbackPopup(String feedbackText) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Customer Feedback");
        alert.setHeaderText("Feedback for Selected Customer:");
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