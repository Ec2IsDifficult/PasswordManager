package sample.Controllers;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Domain.*;
import sample.Model.EncryptDecryptPasswordTable;
import sample.Model.FileUtil;
import sample.Model.MasterPasswordMasterKey;

import java.io.File;
import java.security.Security;
import java.util.Map;

public class HomeController{

    Stage stage;
    EncryptDecryptPasswordTable encDecPasswordTable;
    ObservablePasswordEntryList observablePasswordEntryList;
    MasterPasswordMasterKey masterPasswordMasterKey;

    @FXML
    VBox entryVBox;

    public HomeController() throws Exception{
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        this.observablePasswordEntryList = ObservablePasswordEntryList.getInstance();
        this.encDecPasswordTable = new EncryptDecryptPasswordTable();

    }

    public void checkForExistingPassword() {
        this.masterPasswordMasterKey = MasterPasswordMasterKey.getInstance();

        this.masterPasswordMasterKey.checkForExistingPassword();


    }

    public void loadPasswordTableFromMemory() throws Exception{
        loadAllEntries();
    }

    public void start(Stage stage){
        this.stage = stage;
        this.entryVBox = (VBox) this.stage.getScene().lookup("#entryVBox");
        displayAllEntries();
    }

    public void loadAllEntries() throws Exception{
        this.encDecPasswordTable.decryptPasswordTable();
    }

    public void displayAllEntries(){
        if (this.observablePasswordEntryList.getSize() == 0) {
            this.entryVBox.getChildren().add(new Label("No password entries exist yet..."));
        } else {
            Bindings.bindContent(this.entryVBox.getChildren(), this.observablePasswordEntryList.getPasswordEntries());
        }
    }

    @FXML
    public void saveAllAction(ActionEvent event) throws Exception{
        this.encDecPasswordTable.encryptPasswordTable();
    }

    @FXML
    private void goToCreateNewEntry(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/NewEntry.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        NewEntryController newEntryController = loader.getController();
        newEntryController.start(stage.getScene());
        newEntryController.setObservablePasswordEntryList();
    }
}
