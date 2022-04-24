package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.EncryptDecryptPasswordTable;
import sample.Model.MasterPasswordMasterKey;


public class LoginController {
    private Stage stage;

    // Login controller needs access to the logic that handles decryption and creation of the symmetric key
    private final MasterPasswordMasterKey masterPasswordMasterKey = MasterPasswordMasterKey.getInstance();
    @FXML
    private TextField passwordAttempt;
    @FXML
    private Label feedbackLabel;
    @FXML
    private final EncryptDecryptPasswordTable encDecPasswordTable;

    public LoginController() throws Exception{
        this.encDecPasswordTable = new EncryptDecryptPasswordTable();
    }

    // Starts the controller, gives it the stage and binds FXML objects to the view
    public void start(Stage stage) {
        this.stage = stage;
        this.passwordAttempt = (TextField) this.stage.getScene().lookup("#passwordAttempt");
        this.feedbackLabel = (Label) this.stage.getScene().lookup("#feedbackLabel");
    }

    // Creates the symmetric key by giving it the password attempt
    @FXML
    private void loginAction() throws Exception {
        this.masterPasswordMasterKey.createMasterKey(this.passwordAttempt.getText());
        try{
            // Tries to decrypt the table, if it fails then the exception is caught and a feedback message is given
            // This ensures that when the wrong password is given, the user is given the correct response
            this.encDecPasswordTable.decryptPasswordTable();
            loginSuccessful();
        }catch (Exception e){
            feedbackLabel.setText("Wrong password!");
        }
    }

    // If login is successful then the home view with propper controller is loaded.
    private void loginSuccessful() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/Home.fxml"));
        Parent root = loader.load();
        this.stage.setScene(new Scene(root));
        HomeController homeController = loader.getController();
        homeController.start(this.stage);
    }
}
