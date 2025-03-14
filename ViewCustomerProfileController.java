package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class ViewCustomerProfileController {

    // FXML elements
    @FXML private TextArea nameTextArea;
    @FXML private TextArea emailTextArea;
    @FXML private TextArea passwordTextArea;
    @FXML private TextArea cityTextArea;
    @FXML private TextArea genderTextArea;
    @FXML private TextArea ratingTextArea;
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label passwordLabel;
    @FXML private Label cityLabel;
    @FXML private Label genderLabel;
    @FXML private Label ratingLabel;
    @FXML private Button goBack;
    @FXML private Button goBack1;
    @FXML private Button goBack11;
    @FXML private Button goBack12;
    @FXML private Button goBack14;
    

    private CustomerDBHandler dbHandler;
    int loggedInCustomerId;

    public void initialize() {
        // Initialize the database handler
        dbHandler = new CustomerDBHandler();
        
        // Fetch the logged-in customer from AppData
        
        Customer loggedInCustomer = AppData.getLoggedInCustomer();

        if (loggedInCustomer != null) {
            // Extract the customer ID from the logged-in customer
            loggedInCustomerId = loggedInCustomer.getCustomerId();

            // Populate the fields with the fetched customer data
            nameTextArea.setText(loggedInCustomer.getName());
            emailTextArea.setText(loggedInCustomer.getEmail());
            passwordTextArea.setText(loggedInCustomer.getPassword());
            cityTextArea.setText(loggedInCustomer.getCity());
            genderTextArea.setText(loggedInCustomer.getGender());
            ratingTextArea.setText(String.valueOf(loggedInCustomer.getRating()));

            // You now have the customer ID in the variable loggedInCustomerId
            System.out.println("Logged in Customer ID: " + loggedInCustomerId);
        }
    }
    

    @FXML
    private void goBackAction() {
        // Get the current stage (window)
        Stage stage = (Stage) goBack.getScene().getWindow();
        
        // Load the previous scene (e.g., main menu or login screen)
        try {
            // You can replace this with the path to your previous FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHomePage.fxml"));
            Parent root = loader.load();
            
            // Create a new scene with the loaded FXML
            Scene scene = new Scene(root);
            
            // Set the scene on the stage and show it
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void updateNameAction() {
        // Make the name text area editable when the user wants to update the name
        nameTextArea.setEditable(true);

        // Set focus to the name field for better UX
        nameTextArea.requestFocus();

        // Listen for the Enter key press to save the new name
        nameTextArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newName = nameTextArea.getText();
                dbHandler.updateCustomerName(loggedInCustomerId, newName);  // Update in DB
                nameTextArea.setEditable(false);  // Make the text area non-editable after update
            }
        });
    }

    @FXML
    private void updatePasswordAction() {
        // Make the password text area editable when the user wants to update the password
        passwordTextArea.setEditable(true);

        // Set focus to the password field
        passwordTextArea.requestFocus();

        // Listen for the Enter key press to save the new password
        passwordTextArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newPassword = passwordTextArea.getText();
                dbHandler.updateCustomerPassword(loggedInCustomerId, newPassword);  // Update in DB
                passwordTextArea.setEditable(false);  // Make the text area non-editable after update
            }
        });
    }

    @FXML
    private void updateCityAction() {
        // Make the city text area editable when the user wants to update the city
        cityTextArea.setEditable(true);

        // Set focus to the city field
        cityTextArea.requestFocus();

        // Listen for the Enter key press to save the new city
        cityTextArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newCity = cityTextArea.getText();
                dbHandler.updateCustomerCity(loggedInCustomerId, newCity);  // Update in DB
                cityTextArea.setEditable(false);  // Make the text area non-editable after update
            }
        });
    }
    @FXML
    private void updateGenderAction() {
        // Make the city text area editable when the user wants to update the city
    	genderTextArea.setEditable(true);

        // Set focus to the city field
    	genderTextArea.requestFocus();

        // Listen for the Enter key press to save the new city
    	genderTextArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newGender = genderTextArea.getText();
                dbHandler.updateCustomerGender(loggedInCustomerId, newGender);  // Update in DB
                genderTextArea.setEditable(false);  // Make the text area non-editable after update
            }
        });
    }
    


}