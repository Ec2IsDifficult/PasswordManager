package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Domain.EntryHBox;
import sample.Domain.ObservablePasswordEntryList;
import sample.Model.SuggestStrongPassword;

public class NewEntryController {

    // Singleton password table class wrapped in an observable list
    private ObservablePasswordEntryList observablePasswordEntryList;
    // Class for suggesting strong password
    private SuggestStrongPassword suggestStrongPassword;

    // FXML input fields for user input
    @FXML
    private TextField siteField;
    @FXML
    private TextField urlField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField repeatedPasswordField;
    @FXML
    private Label equalPasswordFeedback;

    public NewEntryController(){
    }

    // Logic for starting the view properly
    public void start(Scene scene) {
        this.siteField = (TextField) scene.lookup("#site");
        this.urlField = (TextField) scene.lookup("#url");
        this.usernameField = (TextField) scene.lookup("#username");
        this.passwordField = (TextField) scene.lookup("#password");
        this.repeatedPasswordField = (TextField) scene.lookup("#passwordRepeat");
        this.equalPasswordFeedback = (Label) scene.lookup("#equalPasswordFeedback");
        this.suggestStrongPassword = new SuggestStrongPassword();
    }

    // Runs when the "change" button for an entry in the howe-view is pressed
    public void setToBeChangedEntry(String site, String url, String username, String password){
        this.siteField.setText(site);
        this.urlField.setText(url);
        this.usernameField.setText(username);
        this.passwordField.setText(password);
    }

    // Getting the singleton instance of the observable list containing the password table
    public void setObservablePasswordEntryList(){
        this.observablePasswordEntryList = ObservablePasswordEntryList.getInstance();
    }

    // Input validation for saving an entry. Runs the addEntry function in the password table singleton class.
    @FXML
    private void saveEntryAction(){

        // No field must be empty
        if(!this.siteField.getText().equals("")
                && !this.urlField.getText().equals("")
                && !this.usernameField.getText().equals("")
                && !this.passwordField.getText().equals("")) {

            // Password and repeated password must be similar
            if(this.passwordField.getText().equals(this.repeatedPasswordField.getText())){
                EntryHBox entryHBox = new EntryHBox(
                        this.siteField.getText(),
                        this.urlField.getText(),
                        this.usernameField.getText(),
                        this.passwordField.getText()
                );
                String response = this.observablePasswordEntryList.addEntry(entryHBox);
                this.equalPasswordFeedback.setText(response);
            }else{
                this.equalPasswordFeedback.setText("Passwords must match!");
            }
        }else{
            this.equalPasswordFeedback.setText("Missing information!");
        }
    }

    // Logic for going back to the home-view. There is a button for this in the new entry view
    @FXML
    private void backToOverviewAction(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/Home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        HomeController homeController = loader.getController();
        homeController.start(stage);
    }

    // Logic for suggesting strong password. This uses the external library "passay"
    @FXML
    private void suggestStrongPasswordAction() {
        String suggestedPassword = this.suggestStrongPassword.generatePassword();
        this.repeatedPasswordField.setText(suggestedPassword);
        this.passwordField.setText(suggestedPassword);
    }
}
