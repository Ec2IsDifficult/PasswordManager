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
        Bindings.bindContent(this.entryVBox.getChildren(), this.observablePasswordEntryList.getPasswordEntries());
        //TODO: Bind this method to run every time something happens. Generally everything that has to do with
        // a happening in the observable, should be able to be listed as a method that should be runned based
        // on this happening
        this.checkIfEmpty();
    }

    public void checkIfEmpty() {
        if (this.entryVBox.getChildren().size() == 0) {
            this.entryVBox.getChildren().add(new Label("No password entries exist yet..."));
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
