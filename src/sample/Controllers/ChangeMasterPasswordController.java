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
import sample.Model.MasterPasswordMasterKey;

import java.nio.charset.StandardCharsets;

public class ChangeMasterPasswordController {
    private Scene scene;

    @FXML
    private TextField oldPassword;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField newPasswordRepeat;
    @FXML
    private Label wrongPassword;
    @FXML
    private Label wrongRepeatPassword;
    private MasterPasswordMasterKey masterPasswordMasterKey;
    public void start(Scene scene) {
        this.scene = scene;
        masterPasswordMasterKey = MasterPasswordMasterKey.getInstance();
        this.oldPassword = (TextField) this.scene.lookup("#oldPassword");
        this.newPassword = (TextField) this.scene.lookup("#newPassword");
        this.newPasswordRepeat = (TextField) this.scene.lookup("#newPasswordRepeat");
    }

    @FXML
    private void changePasswordAction(ActionEvent event) throws Exception{
        if(this.oldPassword.getText().equals(this.masterPasswordMasterKey.getMasterPassword())){
            if (this.newPassword.getText().equals(this.newPasswordRepeat.getText())){
                this.masterPasswordMasterKey.setMasterPassword(this.newPassword.getText());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/Home.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                HomeController homeController = loader.getController();
                homeController.start(stage);
            }else{
                this.wrongRepeatPassword.setText("New passwords don't match!");
            }
        }else{
            this.wrongPassword.setText("Wrong password!");
        }
    }
}
