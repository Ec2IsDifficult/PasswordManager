package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.MasterPasswordMasterKey;


public class LoginController {
    Stage stage;
    MasterPasswordMasterKey masterPasswordMasterKey = MasterPasswordMasterKey.getInstance();

    @FXML
    TextField passwordAttempt;

    @FXML
    Label feedbackLabel;

    public LoginController(){

    }

    public void start(Stage stage) {
        this.stage = stage;
        this.passwordAttempt = (TextField) this.stage.getScene().lookup("#passwordAttempt");
        this.feedbackLabel = (Label) this.stage.getScene().lookup("#feedbackLabel");
    }

    @FXML
    public void loginAction() throws Exception {
        if(this.passwordAttempt.getText().equals(this.masterPasswordMasterKey.getMasterPassword())){
            loginSuccessful();
        }else{
             this.feedbackLabel.setText("Password incorrect...");
        }
    }

    public void loginSuccessful() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/Home.fxml"));
        Parent root = loader.load();
        this.stage.setScene(new Scene(root));
        HomeController homeController = loader.getController();
        homeController.loadPasswordTableFromMemory();
        homeController.start(this.stage);
    }
}
