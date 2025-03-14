package application;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class OfferServicesController {

	private ServiceDBHandler dbHandler;

    // FXML fields for the TableView and other UI components
    @FXML
    private TableView<BuiltInService> tableView2;

    @FXML
    private TableColumn<BuiltInService, String> Name2;

    @FXML
    private TableColumn<BuiltInService, String> Category2;

    @FXML
    private TableColumn<BuiltInService, String> ServiceProvider2;

    @FXML
    private Button addOfferBtn;

    @FXML
    private Label titleLabel;

    // Initialize method to load services into the TableView
    @FXML
    public void initialize() {
        // Set the cell value factories to link TableView columns with BuiltInService properties
        Name2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        Category2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        ServiceProvider2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        // Load services into the TableView
        loadServices();
    }

    // Load all services into the TableView
    private void loadServices() {
        // Assuming BuiltInService.getServices() returns a list of services
        ObservableList<BuiltInService> services = FXCollections.observableArrayList(BuiltInService.getServices());
        tableView2.setItems(services);
    }

    @FXML
    private TextField serviceNameField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField descriptionField;

    @FXML
    private void handleAddOffer() {
        // Get data from the input fields
        String serviceName = serviceNameField.getText();
        String category = categoryField.getText();
        String description = descriptionField.getText();
        
        if (serviceName.isEmpty() && category.isEmpty() && description.isEmpty()) {
            return;
        }

        // Validation to ensure fields are not empty
        if (serviceName.isEmpty() || category.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Please fill all fields");
            alert.setContentText("Service Name, Category, and Description are required.");
            alert.showAndWait();
            return;
        }

        // Create a new Service object with the provided data
        BuiltInService newService = new BuiltInService(
            generateServiceId(), // You can implement a method to generate a unique ID or let the database auto-generate it
            serviceName,
            description,
            category
        );

        // Add the new service to the database
        if (newService.saveToDatabase()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Service Added");
            alert.setHeaderText("Service successfully added!");
            alert.setContentText("The service '" + serviceName + "' has been added to the system.");
            alert.showAndWait();

            // Reload the services in the TableView
            loadServices();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to add service");
            alert.setContentText("There was an error adding the service to the database. Please try again.");
            alert.showAndWait();
        }

        // Clear the fields after submission
        serviceNameField.clear();
        categoryField.clear();
        descriptionField.clear();
    }
    @FXML
    private void offerServiceAction() {
    	dbHandler = new ServiceDBHandler();
        BuiltInService selectedService = tableView2.getSelectionModel().getSelectedItem();

        if (selectedService != null) {
            // Prompt the user for an hourly rate
            TextInputDialog rateDialog = new TextInputDialog();
            rateDialog.setTitle("Offer Service");
            rateDialog.setHeaderText("Add Hourly Rate");
            rateDialog.setContentText("Enter your hourly rate (Rs):");

            Optional<String> result = rateDialog.showAndWait();
            result.ifPresent(rate -> {
                try {
                    double hourlyRate = Double.parseDouble(rate);

                    // Create a new Service object
                    Service newService = new Service(
                        selectedService.getServiceId(),
                        selectedService.getServiceName(),
                        selectedService.getDescription(),
                        hourlyRate,
                        selectedService.getCategory(),
                        AppData.getLoggedInSP().getProviderId()
                    );

                    // Save or handle the new Service object as needed
                    saveServiceToDatabase(newService);
                    
                    System.out.println("New Service Offered: " + newService);

                } catch (NumberFormatException e) {
                    // Handle invalid rate input
                    System.err.println("Invalid hourly rate entered.");
                }
            });
        } else {
            System.out.println("No service selected.");
        }
    }
    private void saveServiceToDatabase(Service service) {
    	dbHandler.addService(service);
        System.out.println("Saving to database: " + service);
    }

    // Example method to generate service ID (if not handled by the database)
    private int generateServiceId() {
        // This is a placeholder; in a real app, the ID might be auto-generated by the database.
        return (int) (Math.random() * 1000);
    }
    
    @FXML
    private Button backBtn;

    @FXML
    private void handleBack() {
        // Close the current window (stage)
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close(); 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SPHomePage.fxml")); // Update with the correct path
            Parent root = loader.load();
            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.show(); // Show the new scene
        } catch (Exception e) {
            e.printStackTrace(); // Handle any loading errors here
        }
    }


    // Handle Table row click (optional, e.g., show more details or open a new window)
    @FXML
    public void handleTableClick(MouseEvent event) {
        BuiltInService selectedService = tableView2.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            // Show an alert with the details of the selected service (just for demonstration)
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Service Details");
            alert.setHeaderText("Details of " + selectedService.getServiceName());
            alert.setContentText("Category: " + selectedService.getCategory() + "\nDescription: " + selectedService.getDescription());
            alert.showAndWait();
        }
    }
}