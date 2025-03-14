package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class SPViewProfileController {

    @FXML
    private TextArea cityta;

    @FXML
    private TextArea emailta;

    @FXML
    private TextArea genderta;

    @FXML
    private Button goBacktoSPHP;

    @FXML
    private TextArea nameta;

    @FXML
    private TextArea passta;

    @FXML
    private TextArea ratingta;

    @FXML
    private Button updatecity;

    @FXML
    private Button updategender;

    @FXML
    private Button updatename;

    @FXML
    private Button updatepass;

    // Database handler instance
    private ServiceProviderDBHandler dbHandler = new ServiceProviderDBHandler();
    private int loggedInServiceProviderId = 1; // Replace with actual logic to get logged-in user ID
    public void initialize() {
        // Initialize the database handler
        dbHandler = new ServiceProviderDBHandler();

        // Fetch the logged-in service provider from AppData
        ServiceProvider loggedInServiceProvider = AppData.getLoggedInSP();

        if (loggedInServiceProvider != null) {
            // Extract the service provider ID from the logged-in service provider
            loggedInServiceProviderId = loggedInServiceProvider.getProviderId();

            // Populate the fields with the fetched service provider data
            nameta.setText(loggedInServiceProvider.getName());
            emailta.setText(loggedInServiceProvider.getEmail());
            passta.setText(loggedInServiceProvider.getPassword());
            cityta.setText(loggedInServiceProvider.getCity());
            genderta.setText(loggedInServiceProvider.getGender());
            ratingta.setText(String.valueOf(loggedInServiceProvider.getRating()));

            // Print the logged-in service provider ID for debugging
            System.out.println("Logged in Service Provider ID: " + loggedInServiceProviderId);
        }
    }

    @FXML
    void goBackToSPHomePage(ActionEvent event) {
        // Logic to go back to the Service Provider Home Page
    }

    @FXML
    void updateCityAction(ActionEvent event) {
        // Make the city text area editable when the user wants to update the city
        cityta.setEditable(true);

        // Set focus to the city field
        cityta.requestFocus();

        // Listen for the Enter key press to save the new city
        cityta.setOnKeyPressed(eventKey -> {
            if (eventKey.getCode() == KeyCode.ENTER) {
                String newCity = cityta.getText();
                dbHandler.updateServiceProviderCity(loggedInServiceProviderId, newCity); // Update in DB
                cityta.setEditable(false); // Make the text area non-editable after update
            }
        });
    }

    @FXML
    void updateGenderAction(ActionEvent event) {
        // Make the gender text area editable when the user wants to update the gender
        genderta.setEditable(true);

        // Set focus to the gender field
        genderta.requestFocus();

        // Listen for the Enter key press to save the new gender
        genderta.setOnKeyPressed(eventKey -> {
            if (eventKey.getCode() == KeyCode.ENTER) {
                String newGender = genderta.getText();
                dbHandler.updateServiceProviderGender(loggedInServiceProviderId, newGender); // Update in DB
                genderta.setEditable(false); // Make the text area non-editable after update
            }
        });
    }

    @FXML
    void updateNameAction(ActionEvent event) {
        // Make the name text area editable when the user wants to update the name
        nameta.setEditable(true);

        // Set focus to the name field
        nameta.requestFocus();

        // Listen for the Enter key press to save the new name
        nameta.setOnKeyPressed(eventKey -> {
            if (eventKey.getCode() == KeyCode.ENTER) {
                String newName = nameta.getText();
                dbHandler.updateServiceProviderName(loggedInServiceProviderId, newName); // Update in DB
                nameta.setEditable(false); // Make the text area non-editable after update
            }
        });
    }

    @FXML
    void updatePasswordAction(ActionEvent event) {
        // Make the password text area editable when the user wants to update the password
        passta.setEditable(true);

        // Set focus to the password field
        passta.requestFocus();

        // Listen for the Enter key press to save the new password
        passta.setOnKeyPressed(eventKey -> {
            if (eventKey.getCode() == KeyCode.ENTER) {
                String newPassword = passta.getText();
                dbHandler.updateServiceProviderPassword(loggedInServiceProviderId, newPassword); // Update in DB
                passta.setEditable(false); // Make the text area non-editable after update
            }
        });
    }
}
