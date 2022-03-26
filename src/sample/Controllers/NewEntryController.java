package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Domain.EntryHBox;
import sample.Domain.ObservablePasswordEntryList;
import sample.Domain.PasswordEntry;

public class NewEntryController {

    @FXML
    private TextField siteField;
    @FXML
    private TextField urlField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    Scene scene;
    ObservablePasswordEntryList observablePasswordEntryList;

    public NewEntryController(){

    }

    public void start(Scene scene) {
        this.scene = scene;
        this.siteField = (TextField) this.scene.lookup("#site");
        this.urlField = (TextField) this.scene.lookup("#url");
        this.usernameField = (TextField) this.scene.lookup("#username");
        this.passwordField = (TextField) this.scene.lookup("#password");
    }

    public void setObservablePasswordEntryList(){
        this.observablePasswordEntryList = ObservablePasswordEntryList.getInstance();
    }

    @FXML
    public void saveEntryAction(){
        EntryHBox entryHBox = new EntryHBox(
            this.siteField.getText(),
            this.urlField.getText(),
            this.usernameField.getText(),
            this.passwordField.getText()
        );
        this.observablePasswordEntryList.addEntry(entryHBox);
    }

    @FXML
    private void backToOverviewAction(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/Home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        HomeController homeController = loader.getController();
        homeController.start(stage);
    }
}
