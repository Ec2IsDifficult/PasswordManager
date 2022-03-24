package sample.Controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.bouncycastle.util.encoders.Hex;
import sample.Domain.EntryHBox;
import sample.Domain.ObservablePasswordEntryList;
import sample.Domain.PasswordEntry;
import sample.Domain.PasswordTable;
import sample.Model.EncryptDecryptPasswordTable;
import sample.Model.FileUtil;
import sample.Model.MasterPasswordMasterKey;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.security.Security;
import java.util.ResourceBundle;

public class HomeController {

    Stage stage;
    EncryptDecryptPasswordTable encDecPasswordTable;
    MasterPasswordMasterKey masterPasswordMasterKey;
    ObservablePasswordEntryList observablePasswordEntryList = new ObservablePasswordEntryList();;

    public HomeController(){
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }


    public void start(Stage stage, ObservablePasswordEntryList observablePasswordEntryList) throws Exception{
        if (observablePasswordEntryList != null){
            this.observablePasswordEntryList = observablePasswordEntryList;
        }
        this.encDecPasswordTable = new EncryptDecryptPasswordTable();
        this.masterPasswordMasterKey = new MasterPasswordMasterKey();
        this.stage = stage;
        loadAllEntries();
        displayAllEntries();
    }

    public void loadAllEntries() throws Exception{
        File file = new File("C:\\Users\\rasse\\IdeaProjects\\PasswordManager\\src\\PasswordTableFile.txt");
        // Read file
        byte[] passwordTableFile = FileUtil.readAllBytes(file.toPath());

        // Decrypt file
        if (passwordTableFile.length != 0) {
            this.masterPasswordMasterKey.createMasterKey("HelloWorld");
            byte[] decryptedPasswordTable = this.encDecPasswordTable.decryptPasswordTable(
                    passwordTableFile, this.masterPasswordMasterKey.getMasterKey()
            );

            // Add table to observable list
            this.observablePasswordEntryList.deserializeAllExistingEntries(decryptedPasswordTable);
        }
    }

    public void displayAllEntries(){
        Scene scene = this.stage.getScene();
        VBox entryVBox = (VBox) scene.lookup("#allEntryVBox");
        if (this.observablePasswordEntryList.getSize() == 0) {
            entryVBox.getChildren().add(new Label("No password entries exist yet..."));
        } else {
            for (EntryHBox e: this.observablePasswordEntryList.getPasswordEntries()) {
                System.out.println(e.getSite());
                System.out.println(e);
                entryVBox.getChildren().add(e);
            }
        }
    }

    @FXML
    public void saveAllAction(ActionEvent event) throws Exception{
        this.masterPasswordMasterKey.createMasterKey("HelloWorld");
        byte[] encryptedPasswordTable = this.encDecPasswordTable.encryptPasswordTable(
                this.observablePasswordEntryList.serializeObservablePasswordEntryList(),
                this.masterPasswordMasterKey.getMasterKey()
                );

        File file = new File("C:\\Users\\rasse\\IdeaProjects\\PasswordManager\\src\\PasswordTableFile.txt");
        FileUtil.write(file.toPath(), encryptedPasswordTable);
    }


    @FXML
    private void goToCreateNewEntry(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/NewEntry.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        NewEntryController newEntryController = loader.getController();
        newEntryController.start(stage.getScene());
        newEntryController.setObservablePasswordEntryList(this.observablePasswordEntryList);
    }
}
